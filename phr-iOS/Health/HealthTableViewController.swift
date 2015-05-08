//
//  HealthTableViewController.swift
//  Health
//
//  Created by David Wu on 2/10/15.
//  Copyright (c) 2015 University of Notre Dame. All rights reserved.
//

import UIKit

class HealthTableViewController: UITableViewController {
	
    let baseURL = "http://m-health.cse.nd.edu:8000/phrService-0.0.1-SNAPSHOT/"
    let sections: Dictionary<String, Dictionary<Int, Dictionary<String, String>>> = [
		"Health Views": [
            0: ["name": "Emergency profile", "url": ""],
            1: ["name": "Weight management", "url": ""]
        ],
		"Measurements": [
            0: ["name": "Glucose",          "url": "",              "legend": []],
            1: ["name": "Blood pressure",   "url": "",              "legend": []],
            2: ["name": "Body dimensions",  "url": "",              "legend": []],
            3: ["name": "Cholesterol",      "url": "chol/chol/",    "legend": ["hdl", "ldl", "tryGlycaride"]],
            4: ["name": "Exercise",         "url": "",              "legend": []]
        ],
		"History": [
            0: ["name": "Appointments",     "url": ""],
            1: ["name": "Allergies",        "url": ""],
            2: ["name": "Immunization",     "url": ""],
            3: ["name": "Medications",      "url": ""],
            4: ["name": "Procedures",       "url": ""],
            5: ["name": "Lab reports",      "url": ""]
        ]
	]

    override func viewDidLoad() {
        super.viewDidLoad()

        //self.navigationItem.rightBarButtonItem = self.editButtonItem()
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
			return nil
		}
	}

    override func numberOfSectionsInTableView(tableView: UITableView) -> Int {
        // Return the number of sections.
        return self.sections.count
    }
	
	override func tableView(tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
		return sectionHeaderForSection(section)
	}

    override func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // Return the number of rows in the section.
		let sectionHeader = sectionHeaderForSection(section)
		let cellsInSection: AnyObject = self.sections[sectionHeader!]!
        return cellsInSection.count
    }

    override func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCellWithIdentifier("cell", forIndexPath: indexPath) as! UITableViewCell
		let sectionHeader = sectionHeaderForSection(indexPath.section)
        let cellTitle = self.sections[sectionHeader!]![indexPath.row]!["name"]!

        // Configure the cell...
		cell.textLabel?.text = cellTitle
        cell.imageView?.image = UIImage(named: cellTitle)

		return cell
    }

    // MARK: - Navigation
    
    override func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath) {
        self.performSegueWithIdentifier("showDetail", sender: self)
    }

    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if (segue.identifier == "showDetail") {
            let destinationViewController = segue.destinationViewController as! HealthDetailViewController
            let indexPath = self.tableView.indexPathForSelectedRow()
            let sectionHeader = sectionHeaderForSection(indexPath!.section)
            let urlSuffix: String = self.sections[sectionHeader!]![indexPath!.row]!["url"]!
            destinationViewController.url = self.baseURL + urlSuffix
            destinationViewController.data = self.sections[sectionHeader!]![indexPath!.row]!["legend"]!
        }
    }
	
}
