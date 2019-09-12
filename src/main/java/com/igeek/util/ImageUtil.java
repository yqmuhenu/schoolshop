package com.igeek.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import com.igeek.dto.ImageHolder;
import net.coobird.thumbnailator.Thumbnails;

import net.coobird.thumbnailator.geometry.Positions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;

public class ImageUtil {

	private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);
	private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
	private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	private static final Random r = new Random();

	public static void main(String[] args) throws IOException {
		File file = new File("C:\\Users\\30624\\Pictures\\ico/littleyellowperson.jpg");
		Thumbnails.of(new FileInputStream(file)).size(200,200)
				.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/watermark.jpg")),0.25f)
		.outputQuality(0.8f).toFile("C:\\Users\\30624\\Pictures\\ico/littleyellowpersonnew.jpg");
	}

	/**
	 * 创建缩略图(添加水印并上传图片),并返回相对路径
	 * @param thumbnail
	 * @param targetAddr 图片相对路径目录（每个shop一个）
	 * @return 返回图片相对路径完整路径名（带图片名及后缀名）：图片相对路径目录+随机名+扩展名
	 */
	public static String generateThumbnail(ImageHolder thumbnail, String targetAddr) {
		//获取不重复的随机名
		String realFileName = getRandomFileName();
		//获取文件的扩展名如png,jpg等
		String extension = getFileExtension(thumbnail.getImageName());
		//如果目标路径不存在，则自动创建
		File targetDir = new File(targetAddr);
		if(!targetDir.exists()){
			makeDirPath(targetAddr);
		}
		//获取文件存储的相对路径(带文件名和扩展名)
		String relativeAddr = targetAddr + realFileName + extension;
		logger.debug("current relativeAddr is : " + relativeAddr);
		//获取文件要保存到的目标路径
		File dest = new File(PathUtil.getImgBasePath() + relativeAddr);//绝对路径
		logger.debug("current complete addr is : " + PathUtil.getImgBasePath() + relativeAddr);
		//调用Thumbnails生成带有水印的图片
		try {
			Thumbnails.of(thumbnail.getImage()).size(200, 200)
					.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/watermark.jpg")),0.25f)
					.outputQuality(0.8f).toFile(dest);
		} catch (IOException e) {
			throw new RuntimeException("创建缩略图失败：" + e.toString());
		}
		//返回图片相对路径地址
		return relativeAddr;
	}

	/**
	 * 处理详情图，并返回新生成图片的相对值路径 (与店铺、商品的缩略图处理只有大小不同)
	 * @param thumbnail 详情图
	 * @param targetAddr 图片相对路径目录
	 * @return
	 */
	public static String generateNormalImg(ImageHolder thumbnail, String targetAddr) {
		//获取不重复的随机名
		String realFileName = getRandomFileName();
		//获取文件的扩展名如png,jpg等
		String extension = getFileExtension(thumbnail.getImageName());
		//如果目标路径不存在，则自动创建
		makeDirPath(targetAddr);
		//获取文件存储的相对路径(带文件名和扩展名)
		String relativeAddr = targetAddr + realFileName + extension;
		logger.debug("current relativeAddr is : " + relativeAddr);
		//获取文件要保存到的目标路径
		File dest = new File(PathUtil.getImgBasePath() + relativeAddr);//绝对路径
		logger.debug("current complete addr is : " + PathUtil.getImgBasePath() + relativeAddr);
		//调用Thumbnails生成带有水印的图片
		try {
			Thumbnails.of(thumbnail.getImage()).size(337, 640)
					.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/watermark.jpg")),0.25f)
					.outputQuality(0.9f).toFile(dest);
		} catch (IOException e) {
			throw new RuntimeException("创建商品详情图片失败：" + e.toString());
		}
		//返回图片相对路径地址
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

	/**
	 * 判断storePath是文件的路径还是目录的路径
	 * 如果是文件路径则删除该文件
	 * 如果storePath是目录路径则删除该目录下的所有文件
	 * @param storePath
	 */
	public static void deleteFileOrPath(String storePath){
		File fileOrPath = new File(PathUtil.getImgBasePath() + storePath);
		if (fileOrPath.exists()) {
			if (fileOrPath.isDirectory()) {
				File files[] = fileOrPath.listFiles();
				for (int i = 0; i < files.length; i++) {
					files[i].delete();
				}
			}
			fileOrPath.delete();
		}
	}

	/*
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
