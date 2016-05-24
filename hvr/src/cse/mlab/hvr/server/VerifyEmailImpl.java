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

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import cse.mlab.hvr.shared.Response;

public class VerifyEmailImpl extends HttpServlet {
	static String serverRoot = "";
	static String signupPath = "";

	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		serverRoot = config.getServletContext().getInitParameter("serverRoot");
		signupPath = config.getServletContext().getInitParameter("signupPath");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			String toWrite = "";
			Response response = verifyEmail(req);
			boolean forward = false;
			if (response.getCode() == 0) {
				forward = true;
				toWrite = "Email verified. You will be redirected to log in.";
			} else if (response.getCode() == 1) {
				toWrite = "Invalid verification code. Please sign up.";
			} else if (response.getCode() == 2) {
				forward = true;
				toWrite = "Email already verified. You will be redirected to log in.";
			} else {
				toWrite = "Service is not available, please try later.";
			}
			resp.getWriter().println(toWrite);

			if (forward) {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				resp.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
				resp.setHeader("Location", "/hvr");
				resp.flushBuffer();
			}
		} catch (Exception e) {
			// TODO: handle exception
			resp.getWriter().println(
					"Service is not available, please try later.");
			e.printStackTrace();
		}

	}

	protected Response verifyEmail(HttpServletRequest request) throws Exception {
		String token = request.getParameter("token");

		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		String url = serverRoot
				+ signupPath + "verification/" + token;
		System.out.println("verification url:" + url);

		WebResource service = client.resource(url);
		String result = service.accept(MediaType.APPLICATION_JSON).get(
				String.class);
		Response response = new Gson().fromJson(result, Response.class);
		return response;

	}

}
