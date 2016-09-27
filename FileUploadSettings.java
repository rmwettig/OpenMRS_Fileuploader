package org.openmrs.module.fileupload;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

public class FileUploadSettings 
{
	private int maxFileSizeValue;
	private String maxFileSizeUnit;
	private Collection<String> allowedFileTypes;
	private Collection<String> disallowedFileTypes;
	
	private String allowedFileTypesString;
	private String disallowedFileTypesString;
	
	public FileUploadSettings()
	{
		maxFileSizeUnit = "M";
		maxFileSizeValue = 2;
		Collection<String> aft = new LinkedHashSet<String>();
		aft.add("jpg");
		aft.add("jpeg");
		aft.add("png");
		aft.add("gif");
		
		Collection<String> dft = new LinkedHashSet<String>();
		dft.add("sh");
		dft.add("exe");
		
		allowedFileTypesString = "jpg,jpeg,png,gif";
		disallowedFileTypesString = "exe,jar,sh";
		
	}
	
	public int getMaxFileSizeValue() 
	{
		return maxFileSizeValue;
	}
	
	public void setMaxFileSizeValue(int maxFileSizeValue) 
	{
		this.maxFileSizeValue = maxFileSizeValue;
	}
	
	public String getMaxFileSizeUnit() 
	{
		return maxFileSizeUnit;
	}
	
	public void setMaxFileSizeUnit(String maxFileSizeUnit) 
	{
		this.maxFileSizeUnit = maxFileSizeUnit;
	}
	
	public String getAllowedFileTypesString() 
	{
		return allowedFileTypesString;
	}

	public void setAllowedFileTypesString(String allowedFileTypesString) 
	{
		this.allowedFileTypesString = allowedFileTypesString;
	}
	
	public String getDisallowedFileTypesString() 
	{
		return disallowedFileTypesString;
	}

	public void setDisallowedFileTypesString(String allowedFileTypesString) 
	{
		this.disallowedFileTypesString = allowedFileTypesString;
	}
	
	public Iterator<String> getAllowedFileTypes()
	{
		return allowedFileTypes.iterator();
	}
	
	public void updateAllowedFileTypes(Collection<String> filetypes)
	{
		allowedFileTypes = filetypes;
	}
	
}
