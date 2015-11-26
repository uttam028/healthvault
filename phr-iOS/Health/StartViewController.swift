//
//  StartViewController.swift
//  Health
//
//  Created by David Wu on 2/2/15.
//  Copyright (c) 2015 University of Notre Dame. All rights reserved.
//

import UIKit

class StartViewController: UIViewController {

	@IBOutlet weak var emailField: UITextField!
	@IBOutlet weak var passwordField: UITextField!
	@IBOutlet weak var errorLabel: UILabel!
	
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
		self.errorLabel.text = ""
    }
	
	func requestLogin(email: String, password: String) {
		self.errorLabel.text = ""
		self.view.endEditing(true)
		var success = false
		let url = NSURL(string: "http://m-health.cse.nd.edu:8000/phrService-0.0.1-SNAPSHOT/login/login")
		let request = NSMutableURLRequest(URL: url!)
		request.HTTPMethod = "PUT"
		request.addValue("application/xml", forHTTPHeaderField: "Content-Type")
		request.HTTPBody = ("<user>" +
							"<email>\(email)</email>" +
							"<password>\(password)</password>" +
							"</user>"
							).dataUsingEncoding(NSUTF8StringEncoding)
		let session = NSURLSession(configuration: NSURLSessionConfiguration.defaultSessionConfiguration())
		let task = session.dataTaskWithRequest(request, completionHandler: { data, response, error in
			if (data == "TRUE".dataUsingEncoding(NSUTF8StringEncoding)) {
				let appDelegate = UIApplication.sharedApplication().delegate as! AppDelegate
				appDelegate.email = email
                appDelegate.loggedIn = true
                
				dispatch_async(dispatch_get_main_queue(), {
					self.performSegueWithIdentifier("login", sender: self)
				})
			} else {
				dispatch_async(dispatch_get_main_queue(), {
					self.errorLabel.text = "Failed to log in"
				})
			}
		})
		task.resume()
	}

	@IBAction func login(sender: UIButton) {
		
		if (self.emailField.text != nil && self.passwordField.text != nil) {
			requestLogin(self.emailField.text, password: self.passwordField.text)
		}
	}
	
	@IBAction func hideKeyboard(sender: UITapGestureRecognizer) {
		self.view.endEditing(true)
	}

}
