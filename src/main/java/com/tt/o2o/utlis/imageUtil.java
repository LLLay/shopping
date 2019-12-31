package com.tt.o2o.utlis;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class imageUtil {


    /**
     * 加水印
     * @param thumbnail
     * @param targetAddr
     * @return
     */
    public static String generateThumbnail(InputStream thumbnailInputStream,String fileName, String targetAddr){

        String realFileName = FileUtil.getRandomFileName();
        String extension = getFileExtension(fileName);
        makeDirPath(targetAddr);
        String relativeAddr = targetAddr + realFileName + extension;
        File dest = new File(FileUtil.getImgBasePath() + relativeAddr);
        try {
            Thumbnails.of(thumbnailInputStream).size(200, 200).outputQuality(0.25f).toFile(dest);
        } catch (IOException e) {
            throw new RuntimeException("创建缩略图失败：" + e.toString());
        }
        return relativeAddr;

    }
    public static String generateNormalImg(InputStream thumbnailInputStream,String fileName, String targetAddr) {
        String realFileName = FileUtil.getRandomFileName();
        String extension = getFileExtension(fileName);
        makeDirPath(targetAddr);
        String relativeAddr = targetAddr + realFileName + extension;
        File dest = new File(FileUtil.getImgBasePath() + relativeAddr);
        try {
            Thumbnails.of(thumbnailInputStream).size(337, 640).outputQuality(0.5f).toFile(dest);
        } catch (IOException e) {
            throw new RuntimeException("创建缩略图失败：" + e.toString());
        }
        return relativeAddr;
    }

    public static List<String> generateNormalImgs(List<InputStream> imgs,String fileName, String targetAddr) {
        int count = 0;
        List<String> relativeAddrList = new ArrayList<String>();
        if (imgs != null && imgs.size() > 0) {
            makeDirPath(targetAddr);
            for (InputStream img : imgs) {
                String realFileName = FileUtil.getRandomFileName();
                String extension = getFileExtension(fileName);
                String relativeAddr = targetAddr + realFileName + count + extension;
                File dest = new File(FileUtil.getImgBasePath() + relativeAddr);
                count++;
                try {
                    Thumbnails.of(img).size(600, 300).outputQuality(0.5f).toFile(dest);
                } catch (IOException e) {
                    throw new RuntimeException("创建图片失败：" + e.toString());
                }
                relativeAddrList.add(relativeAddr);
            }
        }
        return relativeAddrList;
    }

    private static void makeDirPath(String targetAddr) {
        String realFileParentPath = FileUtil.getImgBasePath() + targetAddr;
        File dirPath = new File(realFileParentPath);
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }
    }

    private static String getFileExtension(String fileName) {

        return fileName.substring(fileName.lastIndexOf("."));
    }
    /**
     * storePath 是文件的路径还是目录的路径
     * 如果storePath是文件的路径则删除该文件
     * 如果storePath是目录的路径则删除工目录下的所有文件
     * @param storePath
     */
    public static void deleteFileOrPath(String storePath) {
        File fileOrPath = new File(FileUtil.getImgBasePath() + storePath);
        if (fileOrPath.exists()) {
            // 如果是目录
            if (fileOrPath.isDirectory()) {
                File file[] = fileOrPath.listFiles();
                for (int i = 0; i < file.length; i++) {
                    file[i].delete();
                }
            }
            // 如果是文件
            fileOrPath.delete();
        }
    }
}
