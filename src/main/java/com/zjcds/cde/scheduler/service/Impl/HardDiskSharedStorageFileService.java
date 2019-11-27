package com.zjcds.cde.scheduler.service.Impl;

import com.zjcds.cde.scheduler.service.SharedStorageFileService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.*;

/**
 * created date：2018-08-07
 * @author niezhegang
 */
@Service
@Getter
@Setter
public class HardDiskSharedStorageFileService implements SharedStorageFileService, InitializingBean {
    @Value("${com.zjcds.sharedStorageFile.root}")
    private String rootPath;

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.hasText(rootPath,"com.zjcds.sharedStorageFile.root属性未配置！");
    }

    @Override
    public String rootPath() {
        return rootPath;
    }

    @Override
    public void saveFile(InputStream is, String savePath, String fileName) {
        Assert.notNull(is,"保存的文件流不能为空！");
        Assert.hasText(savePath,"文件保存路径不能为空！");
        Assert.hasText(fileName,"文件名不能为空！");
        try {
            Path filePath = concatFilePath(savePath,fileName);
            ensureFileDirectoryExist(filePath);
            Files.copy(is,filePath, StandardCopyOption.REPLACE_EXISTING);
        }
        catch (Exception ex) {
            throw new IllegalStateException("保存文件出错:路径="+savePath,ex);
        }
    }
    @Override
    public OutputStream getFilePath(String savePath, String fileName){
        Assert.hasText(savePath,"文件保存路径不能为空！");
        Assert.hasText(fileName,"文件名不能为空！");
        OutputStream outputStream=null;
        try {
            Path filePath = concatFilePath(savePath,fileName);
            ensureFileDirectoryExist(filePath);
            outputStream=Files.newOutputStream(filePath);
        }
        catch (Exception ex) {
            throw new IllegalStateException("保存文件出错:路径="+savePath,ex);
        }
        return  outputStream;
    }



    @Override
    public void fetchFile(OutputStream outputStream, String fetchPath, String fileName) {
        Assert.notNull(outputStream,"文件输出流不能为空！");
        Assert.hasText(fetchPath,"文件获取路径不能为空！");
        Assert.hasText(fileName,"文件名不能为空！");
        try {

            Path filePath = concatFilePath(fetchPath,fileName);
            if(!Files.exists(filePath,LinkOption.NOFOLLOW_LINKS))
                throw new IllegalArgumentException("文件不存在!");
            Files.copy(filePath,outputStream);
        }
        catch (Exception ex) {
            throw new IllegalStateException("获取文件出错:路径="+fetchPath,ex);
        }
    }

    @Override
    public void fetchFile(OutputStream outputStream, String filePath) {
        Assert.notNull(outputStream,"文件输出流不能为空！");
        Assert.hasText(filePath,"文件路径不能为空！");
        Path path = Paths.get(filePath);
        fetchFile(outputStream,path.getParent().toString(),path.getFileName().toString());
    }

    /**
     * 确保文件目录存在
     * @param filePath
     */
    private void ensureFileDirectoryExist(Path filePath) throws IOException{
        Assert.notNull(filePath,"文件路径不能为空！");
        Path parentPath = filePath.getParent();
        if(!Files.exists(parentPath,LinkOption.NOFOLLOW_LINKS)){
            Files.createDirectories(parentPath);
        }
    }

    /**
     * 拼接文件路径，如果parentDir为相对路径则拼接rootPath
     * @param parentDir
     * @param fileName
     * @return
     */
    private Path concatFilePath(String parentDir, String fileName) {
        Path parentPath = Paths.get(parentDir);
        if(parentPath.isAbsolute()){
            return Paths.get(parentDir,fileName);
        }
        else {
            return Paths.get(rootPath(),parentDir,fileName);
        }
    }
}
