//
//  RegistrationViewController.swift
//  Health
//
//  Created by David Wu on 2/2/15.
//  Copyright (c) 2015 University of Notre Dame. All rights reserved.
//

import UIKit

class RegistrationViewController: UIViewController {

	@IBOutlet weak var nameField: UITextField!
	@IBOutlet weak var emailField: UITextField!
	@IBOutlet weak var newPasswordField: UITextField!
	@IBOutlet weak var confirmPasswordField: UITextField!
	
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
	
	func requestEmailAvailability(email: String) -> Bool {
		let url = NSURL(string: "http://m-health.cse.nd.edu:8000//phrService-0.0.1-SNAPSHOT/signup/signup/\(email)")
		let request = NSURLRequest(URL: url!)
		let config = NSURLSessionConfiguration.defaultSessionConfiguration()
		let session = NSURLSession(configuration: config)
		let task = session.dataTaskWithRequest(request, completionHandler: { (data, response, error) in
			var jsonError: NSError?
			if let json: AnyObject? = NSJSONSerialization.JSONObjectWithData(data, options: NSJSONReadingOptions.AllowFragments, error: &jsonError) {
				println(json)
			}
		})
		task.resume()
		return true
	}

	@IBAction func join(sender: UIButton) {
		if ((self.emailField.text) != nil) {
			
		}
	}
	
	@IBAction func cancel(sender: UIButton) {
		self.dismissViewControllerAnimated(true, completion: nil)
	}
	
    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
