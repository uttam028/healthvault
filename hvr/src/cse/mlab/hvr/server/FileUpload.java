/*******************************************************************************
 * Copyright 2011 Google Inc. All Rights Reserved.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package cse.mlab.hvr.server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Properties;
import java.util.UUID;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.io.FilenameUtils;


@WebServlet("/fileupload")
@MultipartConfig
public class FileUpload extends HttpServlet {
	static String serverRoot = "";
	static String signupPath = "";
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String upload_location = "/project/speechmarker/speechdata/";

	private static String dbUrl, dbUsername, dbPassword;

	static {
		try {
			Class.forName(DRIVER).newInstance();
			System.out.println("Load DB driver successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		// serverRoot =
		// config.getServletContext().getInitParameter("serverRoot");
		// signupPath =
		// config.getServletContext().getInitParameter("signupPath");

		Properties properties = new Properties();
		InputStream inputStream = getServletContext().getResourceAsStream("/WEB-INF/system.properties");

		try {
			properties.load(inputStream);
			dbUrl = properties.getProperty("db_host") + "/" + properties.getProperty("db_schema")
					+ "?serverTimezone=UTC";
			dbUsername = properties.getProperty("db_username");
			dbPassword = properties.getProperty("db_password");
			System.out.println("db prop, dburl:" + dbUrl + ", user:" + dbUsername + ", pass:" + dbPassword);

			// gmailAccount = properties.getProperty("gmail_account");
			// gmailUser = properties.getProperty("gmail_user");
			// gmailPass = properties.getProperty("gmail_password");
			// System.out.println("Gmail account:"+ gmailAccount + ", user:"+
			// gmailUser + ", gmail pass:"+ gmailPass);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	private Connection connect() {
		try {
			return DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
		
		resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
		
		Part file = req.getPart("file");
        String clientFileName = getFilename(file);
        InputStream inputStream = file.getInputStream();
        
        
		if(!getFileExtension(clientFileName).contains("wav")){
			System.out.println("returning status 400 unsupported file type");
			resp.setStatus(400);
			resp.getWriter().write("Unsupported File Type");
		}else{
			String uniqueFileId = UUID.randomUUID().toString(); 
			String fileName = uniqueFileId + ".wav";
			//String filePath = upload_location	+ _contentDisposition.getFileName();
			String filePath = upload_location	+ fileName;
			System.out.println("file path:"+ filePath);
			

			// save the file to the server
			saveFile(inputStream, filePath);
			double fileSize = getFileSize(filePath);
			System.out.println("file size:"+ fileSize);
			if(fileSize > 30){
				//delete files and retrun error code
				deleteFile(filePath);
				resp.setStatus(400);
				resp.getWriter().write("file size too long");
			}else{
				System.out.println("extension:"+ getFileExtension(filePath) + ", mime type:"+ getMIMEType(filePath));
				Connection connection = null;
				try {
					connection = connect();
					PreparedStatement preparedStatement = connection.prepareStatement("insert into  phr.file_receiver (uuid, original_name, ip, receive_time) values ( ?, ?, ?, now())");
					preparedStatement.setString(1, fileName);
					preparedStatement.setString(2, clientFileName);
					preparedStatement.setString(3, req.getRemoteAddr());
					preparedStatement.execute();
					
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}

			}
			

		}
		

	}
	
	private static String getFilename(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE fix.
            }
        }
        return null;
    }

	
	// save uploaded file to a defined location on the server
	private void saveFile(InputStream _inputStream, String _location) {

		try {
			System.out.println("File write path:" + _location);
			OutputStream _outputStream = new FileOutputStream(new File(_location));
			int read = 0;
			byte[] bytes = new byte[1024];

			_outputStream = new FileOutputStream(new File(_location));
			while ((read = _inputStream.read(bytes)) != -1) {
				_outputStream.write(bytes, 0, read);
			}
			_outputStream.flush();
			_outputStream.close();
			System.out.println("File writing completed");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	private double getFileSize(String filePath){
		try {
			File file =new File(filePath);
			if(file.exists()){

				double bytes = file.length();
				double kilobytes = (bytes / 1024);
				double megabytes = (kilobytes / 1024);
				return megabytes;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return -1;
	}
	
	private String getFileExtension(String filePath){
		try {
			String extension = FilenameUtils.getExtension(filePath);
			System.out.println("file extension : "+ extension);
			return extension;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "";
	}
	
	private String getMIMEType(String filePath){
		try {
			File file = new File(filePath);
			if(file.exists()){
				return URLConnection.guessContentTypeFromName(file.getName());
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "";

	}
	
	private boolean deleteFile(String filePath){
		try {
			File file = new File(filePath);
			return file.delete();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}


}
