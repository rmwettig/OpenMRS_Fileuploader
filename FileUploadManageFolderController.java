package org.openmrs.module.fileupload.web.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openmrs.GlobalProperty;
import org.openmrs.api.context.Context;
import org.openmrs.util.OpenmrsUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("module/fileupload/managefolders")
public class FileUploadManageFolderController 
{
	/**
	 * gets all directories in the specified directory
	 * @param dir directory name
	 * @return all subfolders names
	 */
	private List<String> listSubfolders(String dir)
	{
		List<String> content = new ArrayList<String>();
		try
		{
			
			File fi = new File(dir);
			for(File f : fi.listFiles())
			{
				if(f.isDirectory())
					content.add(f.getName());
			}
		}
		catch(Exception e)
		{
			System.out.println(dir);
		}
		return content;
	}
		
	@RequestMapping(method=RequestMethod.GET)
	public void manageFolders(@RequestParam(required=false, value="breadcrumb") String breadcrumb,ModelMap model)
	{
		model.addAttribute("user", Context.getAuthenticatedUser());
		String appDataDir = OpenmrsUtil.getApplicationDataDirectory();
	
		List<String> c = listSubfolders(appDataDir);
		model.addAttribute("appdir", c);
		model.addAttribute("appDirRoot", appDataDir);
		model.addAttribute("currentPath", appDataDir);
	
//		List<String> c2 = listSubfolders("openmrs-standalone");//new ArrayList<String>();
//		c2.add("openmrs-standalone");
//		model.addAttribute("contextpath", c2);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public void folderOperation(@RequestParam(required=false, value="newDirName")String newDirName,
			@RequestParam(required=true, value="selectedDir")String selectedDir, 
			@RequestParam(required=true, value="dirOp") String folderOp, 
			@RequestParam(required=false, value="breadcrumb") String breadcrumb,
			ModelMap model)
	{
		try 
		{
			
			if(folderOp.equalsIgnoreCase("create"))
			{
				String path = breadcrumb+"/"+newDirName;
				boolean success = new File(path).mkdir();
				
//				String appDataDir = OpenmrsUtil.getApplicationDataDirectory();
				model.addAttribute("appdir", listSubfolders(breadcrumb));
				
//				List<String> c = listSubfolders(breadcrumb);
//				model.addAttribute("appdir", c);
//				model.addAttribute("appDirRoot", appDataDir);
				model.addAttribute("currentPath", breadcrumb);
				
			}
			else if(folderOp.equalsIgnoreCase("delete"))
			{
				//selectedDir contains the current path already
				FileUtils.deleteDirectory(new File(selectedDir));
			}
			else if(folderOp.equalsIgnoreCase("down"))
			{
				String path = selectedDir;//breadcrumb + "/" + selectedDir;
				List<String> content = listSubfolders(path);
				model.addAttribute("currentPath", path);
				model.addAttribute("appdir", content);
			}
			else if(folderOp.equalsIgnoreCase("up"))
			{
				String currPath = breadcrumb;
				String path = currPath.substring(0, currPath.lastIndexOf("/"));
				List<String> content = listSubfolders(path);
				model.addAttribute("currentPath", path);
				model.addAttribute("appdir", content);
			}
			else if (folderOp.equalsIgnoreCase("default"))
			{
//				String appDir = OpenmrsUtil.getApplicationDataDirectory();
				GlobalProperty gp = new GlobalProperty("fileupload.rootDirectory", selectedDir);
				Context.getAdministrationService().saveGlobalProperty(gp);
				model.addAttribute("currentPath", breadcrumb);
				model.addAttribute("appdir", listSubfolders(breadcrumb));
			}
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
