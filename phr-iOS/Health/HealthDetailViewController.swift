//
//  HealthDetailViewController.swift
//  Health
//
//  Created by David Wu on 3/17/15.
//  Copyright (c) 2015 University of Notre Dame. All rights reserved.
//

import UIKit

class HealthDetailViewController: UIViewController, JBChartViewDelegate, JBChartViewDataSource {
    
    @IBOutlet weak var chartView: JBChartView!
    var chartData: [Float] = []
    var url = ""
    var legend = [String]()
    var dataArrays = Array<Array<Float>>()

    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        self.requestHealthDetails()
        self.chartView = JBLineChartView() // TODO: allow either bar or line chart
        self.chartView.delegate = self
        self.chartView.dataSource = self
        //self.requestHealthDetails() // Use this if REST service works
        self.initWithFakeData() // Don't use this if service works
    }
    
    func requestHealthDetails() {
        let appDelegate = UIApplication.sharedApplication().delegate as! AppDelegate
        let email = appDelegate.email
        let url = NSURL(string: self.url + email!)
        let request = NSURLRequest(URL: url!)
        let session = NSURLSession(configuration: NSURLSessionConfiguration.defaultSessionConfiguration())
        let task = session.dataTaskWithRequest(request, completionHandler: { data, response, error in
            println(NSString(data: data, encoding: NSUTF8StringEncoding))
            let jsonDict = NSJSONSerialization.JSONObjectWithData(data, options: .MutableContainers, error: nil) as! NSDictionary
            
            var dateFormatter = NSDateFormatter()
            dateFormatter.dateFormat = "YYYY-MM-DD"
            
            // TODO: convert JSON dict into seperate arrays
            // i.e.: cholesterol dict should be split into arrays for HDL, LDL, triglycaride
        })
        task.resume()
    }
    
    // If service is down, use this function to create data for graph
    func initWithFakeData() {
        for l in self.legend {
            var newLine = [Float]()
            for var i = 0; i < 10; ++i {
                newLine.append( Float(arc4random()) / Float(UINT32_MAX) )
            }
            self.dataArrays.append(newLine)
        }
    }
    
    @IBAction func addMeasurement(sender: UIButton) {
        // TODO: Add popover UI
    }
    
    // MARK: - JBLineChartView data source
    
    func numberOfLinesInLineChartView(lineChartView: JBLineChartView) -> UInt {
        return UInt(self.legend.count)
    }
    
    func lineChartView(lineChartView: JBLineChartView, numberOfVerticalValuesAtLineIndex lineIndex: UInt) -> UInt {
        return UInt(self.dataArrays[lineIndex].count)
    }
    
    // MARK: - JBLineChartView delegate
    
    func lineChartView(lineChartView: JBLineChartView, verticalValueForHorizontalIndex horizontalIndex: UInt, atLineIndex lineIndex: UInt) -> CGFloat {
        return CGFloat(self.dataArrays[lineIndex][horizontalIndex])
    }

}
