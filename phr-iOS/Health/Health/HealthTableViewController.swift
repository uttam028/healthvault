//
//  HealthTableViewController.swift
//  Health
//
//  Created by David Wu on 2/10/15.
//  Copyright (c) 2015 University of Notre Dame. All rights reserved.
//

import UIKit

class HealthTableViewController: UITableViewController {
	
	let sections = [
		"Health Views":[0:"Emergency profile", 1:"Weight management"],
		"Measurements":[0:"Glucose", 1:"Blood pressure", 2:"Body dimensions", 3:"Cholesterol", 4:"Exercise"],
		"History":[0:"Appointments", 1:"Allergies", 2:"Immunization", 3:"Medications", 4:"Procedures", 5:"Lab reports"]]

    override func viewDidLoad() {
        super.viewDidLoad()

        // Uncomment the following line to preserve selection between presentations
        // self.clearsSelectionOnViewWillAppear = false

        // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
        // self.navigationItem.rightBarButtonItem = self.editButtonItem()
    }

    // MARK: - Table view data source
	
	func sectionHeaderForSection(section: Int) -> String? {
		switch (section) {
		case 0:
			return "Health Views"
		case 1:
			return "Measurements"
		case 2:
			return "History"
		default:
			return ""
		}
	}

    override func numberOfSectionsInTableView(tableView: UITableView) -> Int {
        // #warning Potentially incomplete method implementation.
        // Return the number of sections.
        return sections.count
    }
	
	override func tableView(tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
		return sectionHeaderForSection(section)
	}

    override func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete method implementation.
        // Return the number of rows in the section.
        return 0
    }

    override func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCellWithIdentifier("cell", forIndexPath: indexPath) as UITableViewCell
		let sectionHeader = sectionHeaderForSection(indexPath.section)
		let cellsInSection = sections[sectionHeader!]

        // Configure the cell...

        //return cellsInSection[indexPath.row]
		return cell
    }

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using [segue destinationViewController].
        // Pass the selected object to the new view controller.
    }
    */

}
