package com.igeek.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import net.coobird.thumbnailator.Thumbnails;

import net.coobird.thumbnailator.geometry.Positions;

import javax.imageio.ImageIO;

public class ImageUtil {

	private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
	private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	private static final Random r = new Random();

	public static void main(String[] args) throws IOException {
		Thumbnails.of(new File("C:\\Users\\30624\\Pictures\\ico/littleyellowperson.jpg"))
		.size(200,200).watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/watermark.jpg")),0.25f)
		.outputQuality(0.8f).toFile("C:\\Users\\30624\\Pictures\\ico/littleyellowpersonnew.jpg");
	}

	/**
	 * 创建缩略图(添加水印并上传图片),并返回相对路径
	 * @param thumbnail
	 * @param targetAddr
	 * @param originalFileName
	 * @return
	 */
	public static String generateThumbnail(InputStream thumbnail, String targetAddr,String originalFileName) {
		String realFileName = getRandomFileName();//随机名
		String extension = getFileExtension(originalFileName);//扩展名
		makeDirPath(targetAddr);//创建目标路径所涉及到的目录
		String relativeAddr = targetAddr + realFileName + extension;//相对路径
		File dest = new File(PathUtil.getImgBasePath() + relativeAddr);//绝对路径
		try {
			Thumbnails.of(thumbnail).size(200, 200)
					.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/watermark.jpg")),0.25f)
					.outputQuality(0.8f).toFile(dest);
		} catch (IOException e) {
			throw new RuntimeException("创建缩略图失败：" + e.toString());
		}
		return relativeAddr;
	}

	/**
	 * 生成随机文件名，当前年月日小时分钟秒数+五位随机数
	 * @return
	 */
	private static String getRandomFileName() {
		int rannum = r.nextInt(89999) + 10000;
		String nowTimeStr = sDateFormat.format(new Date());
		return nowTimeStr + rannum;
	}

	/**
	 * 获取输入文件流的扩展名
	 * @param originalFileName
	 * @return
	 */
	private static String getFileExtension(String originalFileName) {
		return originalFileName.substring(originalFileName.lastIndexOf("."));
	}

	/**
	 * 创建目标路径所涉及到的目录，即/home/xiangze/image/...
	 * @param targetAddr
	 */
	private static void makeDirPath(String targetAddr) {
		String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
		File dirPath = new File(realFileParentPath);
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}
	}
	/*public static String generateNormalImg(CommonsMultipartFile thumbnail, String targetAddr) {
		String realFileName = FileUtil.getRandomFileName();
		String extension = getFileExtension(thumbnail);
		makeDirPath(targetAddr);
		String relativeAddr = targetAddr + realFileName + extension;
		File dest = new File(FileUtil.getImgBasePath() + relativeAddr);
		try {
			Thumbnails.of(thumbnail.getInputStream()).size(337, 640).outputQuality(0.5f).toFile(dest);
		} catch (IOException e) {
			throw new RuntimeException("创建缩略图失败：" + e.toString());
		}
		return relativeAddr;
	}

	public static List<String> generateNormalImgs(List<CommonsMultipartFile> imgs, String targetAddr) {
		int count = 0;
		List<String> relativeAddrList = new ArrayList<String>();
		if (imgs != null && imgs.size() > 0) {
			makeDirPath(targetAddr);
			for (CommonsMultipartFile img : imgs) {
				String realFileName = FileUtil.getRandomFileName();
				String extension = getFileExtension(img);
				String relativeAddr = targetAddr + realFileName + count + extension;
				File dest = new File(FileUtil.getImgBasePath() + relativeAddr);
				count++;
				try {
					Thumbnails.of(img.getInputStream()).size(600, 300).outputQuality(0.5f).toFile(dest);
				} catch (IOException e) {
					throw new RuntimeException("创建图片失败：" + e.toString());
				}
				relativeAddrList.add(relativeAddr);
			}
		}
		return relativeAddrList;
	}



	*/
}
