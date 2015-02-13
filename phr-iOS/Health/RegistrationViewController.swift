//
//  RegistrationViewController.swift
//  Health
//
//  Created by David Wu on 2/2/15.
//  Copyright (c) 2015 University of Notre Dame. All rights reserved.
//

import UIKit

class RegistrationViewController: UIViewController {

	@IBOutlet weak var firstNameField: UITextField!
	@IBOutlet weak var lastNameField: UITextField!
	@IBOutlet weak var emailField: UITextField!
	@IBOutlet weak var newPasswordField: UITextField!
	@IBOutlet weak var confirmPasswordField: UITextField!
	
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }
	
	func requestEmailAvailability(email: String) -> Bool {
		let url = NSURL(string: "http://m-health.cse.nd.edu:8000/phrService-0.0.1-SNAPSHOT/signup/signup/\(email)")
		let request = NSURLRequest(URL: url!)
		let config = NSURLSessionConfiguration.defaultSessionConfiguration()
		let session = NSURLSession(configuration: config)
		let task = session.dataTaskWithRequest(request, completionHandler: { (data, response, error) in
			println("Data: " + NSString(data: data, encoding: NSUTF8StringEncoding)!)
		})
		task.resume()
		return true
	}
	
	@IBAction func join(sender: UIButton) {
		if ((self.emailField.text) != nil) {
			if (requestEmailAvailability(self.emailField.text) == true) {
				
			}
		}
	}
	
	@IBAction func cancel(sender: UIButton) {
		self.dismissViewControllerAnimated(true, completion: nil)
	}

}
