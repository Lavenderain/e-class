package aurora.controller;

import aurora.model.entity.Userfile;
import aurora.model.page.Json;
import aurora.service.UserfileServiceI;
import aurora.utils.OpenOfficePDFConverter;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
@RequestMapping("/")
public class DocViewerController {

    @Autowired
    private UserfileServiceI userfileService;

    /**
     * 验证该文件是否已经转码,
     *
     * @param id
     * @return
     */
    @RequestMapping("checkFile")
    @ResponseBody
    public Json checkHasPDF(String id) {
        if (!StringUtils.isEmpty(id)) {
            Userfile userfile = userfileService.getById(id);
            if (userfile.getHaspdf() == 1) {
                return new Json<String>(true, "", null);
            } else {
                return new Json<String>(false, "", null);
            }
        } else {
            return new Json<String>(false, "请求参数缺失", null);
        }
    }

    /**
     * 转码请求
     *
     * @param id
     * @param request
     * @return
     */
    @RequestMapping("convertFile")
    @ResponseBody
    public Json convertFile(String id, HttpServletRequest request) {
        //String officeHome = "D:" + File.separator + "Program Files" + File.separator + "LibreOffice 5";
        String officeHome = "C:" + File.separator + "Program Files" + File.separator + "LibreOffice 5";
        String pdfFilePath;
        if (!StringUtils.isBlank(id)) {
            ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
            SessionFactory fac = (SessionFactory) ctx.getBean("sessionFactory");
            Session session = fac.openSession();
            Userfile userfile = session.get(Userfile.class, id);
            String sourceFilePath = userfile.getFile();
            session.close();
            File sourceFile = new File(sourceFilePath);
            if (sourceFile.exists()) {
                pdfFilePath = sourceFile.getParent() + File.separator + System.currentTimeMillis() + ".pdf";
                File pdfFile = new File(pdfFilePath);
                OpenOfficePDFConverter.toPDF(officeHome, sourceFile, pdfFile);
                session = fac.openSession();
                userfile = session.get(Userfile.class, id);
                userfile.setHaspdf((short) 1);
                userfile.setPdffile(pdfFilePath);
                session.beginTransaction();
                session.update(userfile);
                session.getTransaction().commit();
                session.close();
            }
            return new Json<String>(true, "已完成转码", null);
        } else {
            return new Json<String>(false, "请求参数缺失", null);
        }
    }

    /**
     * 获取pdf文件
     * @param id
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("getFile")
    @ResponseBody
    public void getPdfFile(String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String filePath = null;
        if (!StringUtils.isBlank(id)) {
            ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
            SessionFactory fac = (SessionFactory) ctx.getBean("sessionFactory");
            Session session = fac.openSession();
            Userfile userfile = session.get(Userfile.class, id);
            filePath = userfile.getPdffile();
            session.close();
            File f = new File(filePath);
                response.reset();
                response.setCharacterEncoding("utf-8");
                response.setContentType("application/pdf");
                OutputStream os = new BufferedOutputStream(response.getOutputStream());
                FileInputStream is = new FileInputStream(f);
                BufferedInputStream bis = new BufferedInputStream(is);
                byte[] buffer = new byte[1024];
                int length = 0;
                while ((length = bis.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }
                is.close();
                bis.close();
                os.flush();
                os.close();
            }

        }

    }
