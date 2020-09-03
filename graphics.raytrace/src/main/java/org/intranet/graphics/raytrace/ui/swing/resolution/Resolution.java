package org.intranet.graphics.raytrace.ui.swing.resolution;

public class Resolution
{
	private String name;
	public String getName() { return name; }
	public void setName(String value) { name = value; }

	private int width;
	public int getWidth() { return width; }
	public void setWidth(int value) { width = value; }

	private int height;
	public int getHeight() { return height; }
	public void setHeight(int value) { height = value; }

	private double aspect;
	public double getAspect() { return aspect; }
	public void setAspect(double value) { aspect = value; }

	public Resolution(String name, int width, int height, double aspect)
	{
		super();
		this.name = name;
		this.width = width;
		this.height = height;
		this.aspect = aspect;
	}

	@Override
	public String toString()
	{
		return String.format("%s (%dx%d)", name, width, height);
	}

	public static final Resolution SQUARE_400 = new Resolution("400x400", 400, 400, 1.0);
	public static final Resolution SQUARE_500 = new Resolution("500x500", 500, 500, 1.0);
	public static final Resolution VGA = new Resolution("VGA", 640, 480, 1.0);
	public static final Resolution MINI = new Resolution("Mini", 80, 60, 1.0);
	public static final Resolution LOW = new Resolution("Low", 320, 240, 1.0);
	public static final Resolution TGA = new Resolution("TGA", 512, 486, 1.266);
	public static final Resolution PAR = new Resolution("Par", 752, 486, 0.875);
	public static final Resolution DV = new Resolution("DV", 720, 480, 0.889);
	public static final Resolution D1_NTSC = new Resolution("D1 - NTSC", 720, 480, 0.889);
	public static final Resolution D1_PAL = new Resolution("D1 - PAL", 720, 576, 1.066);
	public static final Resolution HDTV_90p = new Resolution("HDTV - 90p", 160, 90, 1.0);
	public static final Resolution HDTV_180p = new Resolution("HDTV - 180p", 320, 180, 1.0);
	public static final Resolution HDTV_360p = new Resolution("HDTV - 360p", 640, 360, 1.0);
	public static final Resolution HDTV_720p = new Resolution("HDTV - 720p", 1280, 720, 1.0);
	public static final Resolution HDTV_1080p = new Resolution("HDTV - 1080p", 1920, 1080, 1.0);
	public static final Resolution UHD_4K = new Resolution("4K UHD", 3840, 2160, 1.0);
	public static final Resolution UHD_8K = new Resolution("8K UHD", 7680, 4320, 1.0);
	public static final Resolution PANAVISION = new Resolution("Panavision", 2048, 872, 1.0);
	public static final Resolution VISTAVISION = new Resolution("VistaVision", 2048, 1366, 1.0);
	public static final Resolution SUPER35 = new Resolution("Super35", 2048, 1536, 1.0);
	public static final Resolution DCI_2K_CROPPED = new Resolution("DCI 2K cropped", 2048, 858, 1.0);
	public static final Resolution DCI_2K_NATIVE = new Resolution("DCI 2K native", 2048, 1080, 1.0);
	public static final Resolution DCI_4K_CROPPED = new Resolution("DCI 4K cropped", 4096, 1716, 1.0);
	public static final Resolution DCI_4K_NATIVE = new Resolution("DCI 4K native", 4096, 2160, 1.0);

	public static final Resolution[] resolutions = new Resolution[] {
		SQUARE_400,
		SQUARE_500,
		VGA,
		MINI,
		LOW,
		TGA,
		PAR,
		DV,
		D1_NTSC,
		D1_PAL,
		HDTV_90p,
		HDTV_180p,
		HDTV_360p,
		HDTV_720p,
		HDTV_1080p,
		UHD_4K,
		UHD_8K,
		PANAVISION,
		VISTAVISION,
		SUPER35,
		DCI_2K_CROPPED,
		DCI_2K_NATIVE,
		DCI_4K_CROPPED,
		DCI_4K_NATIVE
	};
}