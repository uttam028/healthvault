//
//  RegistrationViewController.swift
//  Health
//
//  Created by David Wu on 2/2/15.
//  Copyright (c) 2015 University of Notre Dame. All rights reserved.
//

import UIKit

class RegistrationViewController: UIViewController, UIBarPositioningDelegate {

	@IBOutlet weak var firstNameField: UITextField!
	@IBOutlet weak var lastNameField: UITextField!
	@IBOutlet weak var emailField: UITextField!
	@IBOutlet weak var newPasswordField: UITextField!
	@IBOutlet weak var confirmPasswordField: UITextField!
    @IBOutlet weak var responseLabel: UILabel!
	
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        self.responseLabel.text = ""
    }
	
	func requestEmailAvailability(email: String) {
		let url = NSURL(string: "http://m-health.cse.nd.edu:8000/phrService-0.0.1-SNAPSHOT/signup/signup/\(email)")
		let request = NSURLRequest(URL: url!)
		let session = NSURLSession(configuration: NSURLSessionConfiguration.defaultSessionConfiguration())
		let task = session.dataTaskWithRequest(request, completionHandler: { (data, response, error) in
			if (data == "FALSE".dataUsingEncoding(NSUTF8StringEncoding)) {
				self.requestRegistration(self.firstNameField.text, lastName: self.lastNameField.text, email: self.emailField.text, password: self.newPasswordField.text)
			}
		})
		task.resume()
	}
	
	func requestRegistration(firstName: String, lastName: String, email: String, password: String) {
		let url = NSURL(string: "http://m-health.cse.nd.edu:8000/phrService-0.0.1-SNAPSHOT/signup/signup")
		let request = NSMutableURLRequest(URL: url!)
		request.HTTPMethod = "PUT"
		request.addValue("application/xml", forHTTPHeaderField: "Content-Type")
		request.HTTPBody = ("<user>" +
							"<firstName>\(firstName)</firstName>" +
							"<lastName>\(lastName)</lastName>" +
							"<email>\(email)</email>" +
							"<password>\(password)</password>" +
							"</user>"
							).dataUsingEncoding(NSUTF8StringEncoding)
		let session = NSURLSession(configuration: NSURLSessionConfiguration.defaultSessionConfiguration())
		let task = session.dataTaskWithRequest(request, completionHandler: { data, response, error in
            println(NSString(data: data, encoding: NSUTF8StringEncoding))
            if (data == "The user \(email) successfully signed up.".dataUsingEncoding(NSUTF8StringEncoding)) {
                dispatch_async(dispatch_get_main_queue(), {
                    self.responseLabel.textColor = UIColor.blackColor()
                    self.responseLabel.text = "Signed up!"
                })
                NSThread.sleepForTimeInterval(1.5)
                dispatch_async(dispatch_get_main_queue(), {
                    self.performSegueWithIdentifier("userRegistered", sender: self)
                })
            } else {
                dispatch_async(dispatch_get_main_queue(), {
                    self.responseLabel.textColor = UIColor.redColor()
                    self.responseLabel.text = "Failed to sign up"
                })
            }
		})
        task.resume()
	}
	
	@IBAction func join(sender: UIButton) {
		if (self.emailField.text != nil) {
			requestEmailAvailability(self.emailField.text)
		}
	}
	
	func positionForBar(bar: UIBarPositioning) -> UIBarPosition {
		return .TopAttached
	}
	
	@IBAction func cancel(sender: UIBarButtonItem) {
		self.dismissViewControllerAnimated(true, completion: nil)
	}

}
