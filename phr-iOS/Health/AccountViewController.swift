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
	
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
	func positionForBar(bar: UIBarPositioning) -> UIBarPosition {
		return .TopAttached
	}
	
	@IBAction func close(sender: UIBarButtonItem) {
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
