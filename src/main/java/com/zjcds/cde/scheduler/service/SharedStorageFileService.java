package com.zjcds.cde.scheduler.service;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * 共享存储文件服务
 * created date：2018-08-07
 * @author niezhegang
 */
public interface SharedStorageFileService {
    /**设备提供商的媒体文件存放子目录（相对共享存储的根路径）*/
    public static final String DeviceProviderMediaFileDir = "provider/medias";

    /**
     * 共享存储的根路径
     * @return
     */
    public String rootPath();

    /**
     * 保存文件
     * @param is         文件输入流
     * @param savePath   文件相对root的保存路径
     * @param fileName   文件名
     */
    public void saveFile(InputStream is, String savePath, String fileName);

    /**
     * 获取文件
     * @param outputStream  文件输出流
     * @param fetchPath     文件相对root的获取路径
     * @param fileName      文件名
     */
    public void fetchFile(OutputStream outputStream, String fetchPath, String fileName);

    /**
     * 获取文件
     * @param outputStream  文件输出流
     * @param filePath     文件相对root的获取路径（包含文件名）
     */
    public void fetchFile(OutputStream outputStream, String filePath);

    /**
     * 获取输出流
     * @param savePath
     * @param fileName
     * @return
     */
    public OutputStream getFilePath(String savePath, String fileName);


}
