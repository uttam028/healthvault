//
//  AccountViewController.swift
//  Health
//
//  Created by David Wu on 2/13/15.
//  Copyright (c) 2015 University of Notre Dame. All rights reserved.
//

import UIKit

class AccountViewController: UIViewController, UIBarPositioningDelegate {

	@IBOutlet weak var firstNameField: UITextField!
	@IBOutlet weak var lastNameField: UITextField!
	@IBOutlet weak var phoneField: UITextField!
	@IBOutlet weak var addressField: UITextField!
	@IBOutlet weak var cityField: UITextField!
	@IBOutlet weak var stateField: UITextField!
	@IBOutlet weak var zipField: UITextField!
    let appDelegate = UIApplication.sharedApplication().delegate as! AppDelegate
	
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }
	
	override func viewWillAppear(animated: Bool) {
		let email = appDelegate.email
		let url = NSURL(string: "http://m-health.cse.nd.edu:8000/phrService-0.0.1-SNAPSHOT/profile/profile/\(email!)")
		let request = NSURLRequest(URL: url!)
		let session = NSURLSession(configuration: NSURLSessionConfiguration.defaultSessionConfiguration())
		let task = session.dataTaskWithRequest(request, completionHandler: { data, response, error in
			println(NSString(data: data, encoding: NSUTF8StringEncoding))
			let jsonDict = NSJSONSerialization.JSONObjectWithData(data, options: .MutableContainers, error: nil) as! NSDictionary
			dispatch_async(dispatch_get_main_queue(), {
				self.firstNameField.text = jsonDict.objectForKey("firstName") as! String
				self.lastNameField.text = jsonDict.objectForKey("lastName") as! String
				let phone = jsonDict.objectForKey("mobileNum") as! String
				if (phone != "0") {
					self.phoneField.text = phone
				}
			})
		})
		task.resume()
	}
    
	func positionForBar(bar: UIBarPositioning) -> UIBarPosition {
		return .TopAttached
	}
	
	@IBAction func close(sender: UIBarButtonItem) {
		self.dismissViewControllerAnimated(true, completion: nil)
	}

    @IBAction func logout(sender: UIBarButtonItem) {
        appDelegate.loggedIn = false
        // TODO: return to login
    }
}
