package aurora.controller;

import aurora.model.entity.*;
import aurora.model.page.Json;
import aurora.service.ClassServiceI;
import aurora.service.FolderServiceI;
import aurora.service.SharefileServiceI;
import aurora.service.UserfileServiceI;
import aurora.utils.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/")
public class SharefileController {

    @Autowired
    private ClassServiceI classService;

    @Autowired
    private FolderServiceI folderService;

    @Autowired
    private SharefileServiceI sharefileService;

    @Autowired
    private UserfileServiceI userfileService;

    /**
     * 新建一个文件夹
     * @param pfid 新建文件夹的父文件夹id，也可能是班级id,最终需要根据这个值确定新建文件夹的父文件夹
     * @param name 文件夹名称
     * @return
     */
    @RequestMapping("tea/createFolder")
    @ResponseBody
    public Json createFolder(String pfid, String name){
        Folder pfolder = null;
        if(!StringUtils.isBlank(pfid)){
            //父文件夹
            pfolder = folderService.getById(pfid);
            if(pfolder == null){
                //如果父文件夹不存在，尝试获取班级的根文件夹作为文件夹
                pfolder = folderService.getFolderByClassId(pfid);
                if(pfolder == null){
                    //如果再失败，则为异常情况
                    return new Json<Folder>(false,"父文件夹不存在，父文件夹不可为空",null);
                }
            }
            Folder f = new Folder();
            f.setPfolder(pfolder);
            f.setName(name);
            folderService.save(f);
            Folder currentFolder = folderService.getById(pfid);

            return new Json<Folder>(true,"新建文件夹成功",currentFolder);
        }

        return new Json<Folder>(false,"参数缺失",null);

    }

    /**
     * 获取班级文件
     * @param classId 班级id
     * @return
     */
    @RequestMapping("getSharefiles")
    @ResponseBody
    public Json<Folder> getSharefiles(String classId){
        Folder f = null;
        if(!StringUtils.isBlank(classId)){
            f = folderService.getFolderByClassId(classId);
        }
        return new Json<Folder>(true,"获取班级文件夹成功",f);
    }

    /**
     * 删除一个文件夹
     * @param id 文件夹id
     * @return
     */
    @RequestMapping("tea/deleteFolder")
    @ResponseBody
    public Json<String> deleteFolder(String id){
        if(!StringUtils.isBlank(id)){
            Folder f = folderService.getById(id);
            if(f != null){
                folderService.delete(f);
                return new Json<String>(true,"文件夹已删除",null);
            }else{
                return new Json<String>(false,"文件夹不存在",null);
            }
        }else{
            return new Json<String>(false,"请求参数缺失",null);
        }
    }

    /**
     * 删除一个sharefile文件
     * @param id
     * @return
     */
    @RequestMapping("tea/deleteSharefile")
    @ResponseBody
    public Json deleteSharefile(String id){
        if(!StringUtils.isBlank(id)){
            Sharefile f = sharefileService.getById(id);
            if(f != null){
                Userfile userfile = f.getUserfile();
                userfile.setDeleted((short)1);
                userfileService.update(userfile);
                sharefileService.delete(f);
                return new Json<String>(true,"文件已删除",null);
            }else{
                return new Json<String>(false,"文件不存在",null);
            }
        }else{
            return new Json<String>(false,"请求参数缺失",null);
        }



    }


    /**
     * 教师上传文件(文件上传后，还未经系统确认要长期保存，需要再次确认系统才能长期保存)
     *
     * @param file
     * @param req
     * @return 文件Id
     * @throws IOException
     */
    @RequestMapping("tea/uploadSharefile")
    @ResponseBody
    public Json saveUploaFile(@RequestParam("file") MultipartFile file, HttpServletRequest req) throws IOException {
        //ApplicationContext ctx = new FileSystemXmlApplicationContext(new String[]{"classpath:spring.xml", "classpath:spring-hibernate.xml"});
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(req.getSession().getServletContext());
        SessionFactory fac = (SessionFactory) ctx.getBean("sessionFactory");

        Map<String, String> fileInfo = new HashMap<String, String>();
        String fileId = "";
        String realPath = req.getSession().getServletContext().getRealPath("/");
        String fileOrganization = "userfile" + File.separator + "sharefile" + File.separator;
        String directoryPath = realPath + fileOrganization;
        File directory = new File(directoryPath);
        if (!directory.exists()) {//如果目录不存在，创建目录
            directory.mkdirs();
        }
        if (!file.isEmpty()) {
            String time = System.currentTimeMillis() + "";
            file.transferTo(new File(directory + File.separator + time + file.getOriginalFilename()));
            fileInfo.put("name", file.getOriginalFilename());
            fileInfo.put("type", file.getContentType());
            fileInfo.put("extension", StringUtil.getFileExtension(file.getOriginalFilename()));
            fileInfo.put("file", directory + File.separator + time + file.getOriginalFilename());
        }

        Session session = fac.openSession();
        Userfile userfile = new Userfile();
        userfile.setFile(fileInfo.get("file"));
        userfile.setIstemp((short) 1);
        userfile.setType(fileInfo.get("type"));
        userfile.setName(fileInfo.get("name"));
        userfile.setExtension(fileInfo.get("extension"));
        session.beginTransaction();
        session.save(userfile);
        session.getTransaction().commit();
        fileId = userfile.getId();
        session.close();
        return new Json<String>(true, "文件已保存", fileId);
    }

    /**
     * 移除文件（移除已经上传的文件）
     * @param fid
     * @return
     */
    @RequestMapping("tea/removeSharefile")
    @ResponseBody
    public Json removeUploadFile(String fid){
        if(!StringUtils.isBlank(fid)){
            Userfile userfile = userfileService.getById(fid);
            userfile.setDeleted((short)1);
            userfileService.update(userfile);
        }
        return new Json<String>(true,"文件已移除",null);
    }

    /**
     * 保存上传的文件
     * @param fid
     * @param files
     * @return
     */
    @RequestMapping("tea/saveUploadSharefile")
    @ResponseBody
    public Json saveUploadSharefile(String fid, String[] files){
        if(!StringUtils.isBlank(fid) && files.length > 0){
        Folder f = folderService.getById(fid);
        if(f != null){
            for(String userFileId: files){
                Userfile userfile = userfileService.getById(userFileId);
                userfile.setIstemp((short)0);
                userfileService.update(userfile);

                Sharefile sharefile = new Sharefile();
                sharefile.setUserfile(userfile);
                sharefile.setName(userfile.getName());
                sharefile.setFolder(f);
                sharefileService.save(sharefile);
            }
        }
            Folder savedFolder = folderService.getById(fid);

            return new Json<Folder>(true,"文件已成功上传",savedFolder);
        }else{
            return new Json<Folder>(false,"参数缺失",null);
        }


    }

    /**
     * 文件下载
     * @param sharefileId
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("downloadSharefile")
    @ResponseBody
    public void downloadSharefile(String sharefileId,HttpServletRequest request,HttpServletResponse response) throws Exception {
        String filePath = null;
        String fileName = null;

            if(!StringUtils.isBlank(sharefileId)){
                //ApplicationContext ctx = new FileSystemXmlApplicationContext(new String[]{"classpath:spring.xml", "classpath:spring-hibernate.xml"});
                //ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getSession().getServletContext());
                ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());

                SessionFactory fac = (SessionFactory) ctx.getBean("sessionFactory");
                Session session = fac.openSession();
                Sharefile sharefile = session.get(Sharefile.class,sharefileId);

                if(sharefile.getUserfile() != null){
                    filePath = sharefile.getUserfile().getFile();
                    fileName = sharefile.getUserfile().getName();
                }
                session.close();

                if(filePath!=null && fileName != null){
                    File f = new File(filePath);
                    if(f.exists()){
                        response.reset();
                        response.setCharacterEncoding("utf-8");
                        //response.setContentType("multipart/form-data");//这样会自动判断类型
                        response.setContentType("application/force-download");// 设置强制下载不打开
                        response.setHeader("Content-Disposition", "attachment;fileName=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));

                        //response.addHeader("Content-Disposition","attachment;fileName=" + fileName);// 设置文件名
                        OutputStream os = new BufferedOutputStream(response.getOutputStream());
                        FileInputStream is = new FileInputStream(f);
                        BufferedInputStream bis = new BufferedInputStream(is);
                        byte[] buffer=new byte[1024];
                        int length=0;

                        while((length=bis.read(buffer))>0){
                            os.write(buffer,0,length);
                        }
                        is.close();
                        bis.close();
                        os.flush();
                        os.close();
                        //更新下载次数
                        session = fac.openSession();
                        sharefile = session.get(Sharefile.class,sharefileId);
                        sharefile.setDownloadtimes(sharefile.getDownloadtimes()+1);
                        session.beginTransaction();
                        session.update(sharefile);
                        session.getTransaction().commit();
                        session.close();

                    }

                }

            }



    }







}
