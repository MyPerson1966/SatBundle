package pns.controllers;

import java.io.*;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import java.io.InputStream;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

@Named
@RequestScoped
public class FileDownloadController {

    public FileDownloadController() {
    }

    /**
     * Download given file.
     */
    public void downloadFile(File file) throws IOException {
        InputStream fis = new FileInputStream(file);
        byte[] buf = new byte[1024];
        int offset = 0;
        int numRead = 0;
        while ((offset < buf.length) && ((numRead = fis.read(buf, offset, buf.length - offset)) >= 0)) {
            offset += numRead;
        }
        fis.close();
        HttpServletResponse response
                = (HttpServletResponse) FacesContext.getCurrentInstance()
                        .getExternalContext().getResponse();

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + file.getName());
        response.getOutputStream().write(buf);
        response.getOutputStream().flush();
        response.getOutputStream().close();
        FacesContext.getCurrentInstance().responseComplete();
    }
}
