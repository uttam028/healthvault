$(function() {
	var $uploadStatus = $('#upload_status'), $showLevelButton = $('.show_level'), $hideLevelButton = $('.hide_level'), $level = $('.control_panel .level');

	var CLASS_CONTROLS = "control_panel";
	var CLASS_RECORDING = "recording";
	var CLASS_PLAYBACK_READY = "playback_ready";
	var CLASS_PLAYING = "playing";
	var CLASS_PLAYBACK_PAUSED = "playback_paused";

	// Embedding flash object
	// ---------------------------------------------------------------------------------------------

	setUpFormOptions();
	var appWidth = 1;
	var appHeight = 1;
	// var flashvars = {'upload_image': 'images/upload.png'};
	var params = {};
	var attributes = {
		'id' : "recorderApp",
		'name' : "recorderApp"
	};
	// swfobject.embedSWF("recorder.swf", "flashcontent", appWidth, appHeight,
	// "11.0.0", "", flashvars, params, attributes);
	swfobject.embedSWF("recorder.swf", "flashcontent", appWidth, appHeight,
			"11.0.0", "", {}, params, attributes);

	// Handling FWR events
	// ------------------------------------------------------------------------------------------------

	window.fwr_event_handler = function fwr_event_handler() {
		// $('#status').text("Last recorder event: " + arguments[0]);
		var name, $controls;
		switch (arguments[0]) {
		case "ready":
			var width = parseInt(arguments[1]);
			var height = parseInt(arguments[2]);
			// FWRecorder.uploadFormId = "#uploadForm";
			// FWRecorder.uploadFieldName = "upload_file[filename]";
			FWRecorder.connect("recorderApp", 0);
			FWRecorder.recorderOriginalWidth = width;
			FWRecorder.recorderOriginalHeight = height;
			// $('.save_button').css({'width': width, 'height': height});
			console.log("main.js: ready for something..");
			break;

		case "no_microphone_found":
			console.log("main.js: no microphone found");
			break;

		case "microphone_user_request":
			recorderEl().addClass("floating");
			FWRecorder.showPermissionWindow();
			console.log("main.js: microphone user request");
			break;

		case "microphone_connected":
			FWRecorder.isReady = true;
			// $uploadStatus.css({'color': '#000'});
			console.log("main.js: microphone connected");
			break;

		case "permission_panel_closed":
			FWRecorder.defaultSize();
			recorderEl().removeClass("floating");
			FWRecorder.hide()
			console.log("main.js: permission panel closed");
			break;

		case "microphone_activity":
			// $('#activity_level').text(arguments[1]);
			try {
				/*
				var text = "";
				for(i=0;i<arguments.length;i++){
					text.concat(arguments[i]).concat("-");
				}
				alert("name of record".concat(text));*/
				window.enableStopButton();
			} catch (e) {
				// TODO: handle exception
				console.log("got exception call stop " + e.message);
			}
			console.log("main.js: case microphone activity");
			break;

		case "recording":
			name = arguments[1];
			$controls = controlsEl(name);
			//FWRecorder.observeLevel();
			FWRecorder.hide();
			setControlsClass($controls, CLASS_RECORDING);
			console.log("main.js: case recording");
			break;

		case "recording_stopped":
			name = arguments[1];
			console.log("main.js: stop method, name:" + name);
			$controls = controlsEl(name);
			var duration = arguments[2];
			// FWRecorder.show();
			setControlsClass($controls, CLASS_PLAYBACK_READY);
			// $('#duration').text(duration.toFixed(4) + " seconds");
			console.log("main.js: case recording stopped");
			try {
				if (name.toLowerCase().indexOf("mic") == 0) {
					window.resetMictestUI();
				} else {
					window.enablePlayButton();
				}
			} catch (e) {
				// TODO: handle exception
				console.log("got exception to call play button " + e.message);
			}
			break;

		case "microphone_level":
			// $level.css({width: arguments[1] * 50 + '%'});
			console.log("main.js: case microphone level" + (arguments[1] * 50)
					+ '%');
			/*
			var text = "";
			for(i=0;i<arguments.length;i++){
				text.concat(arguments[i]).concat("-");
			}
			console.log("mic level".concat(text));
			*/
			try {
				window.updateVolume(arguments[1]);
			} catch (e) {
				// TODO: handle exception
				console.log("got exception to update volume text " + e.message);
			}
			break;

		case "observing_level":
			// $showLevelButton.hide();
			// $hideLevelButton.show();
			console.log("main.js: observing_level");
			break;

		case "observing_level_stopped":
			// $showLevelButton.show();
			// $hideLevelButton.hide();
			// $level.css({width: 0});
			console.log("main.js: observing_level_stopped and will call wnd.stop(5)");
			break;

		case "playing":
			name = arguments[1];
			$controls = controlsEl(name);
			setControlsClass($controls, CLASS_PLAYING);
			console.log("main.js: case playing");
			break;

		case "playback_started":
			name = arguments[1];
			var latency = arguments[2];
			console.log("main.js: case playback started");
			//FWRecorder.observeLevel();
			window.enablePauseButton();
			break;

		case "stopped":
			name = arguments[1];
			$controls = controlsEl(name);
			setControlsClass($controls, CLASS_PLAYBACK_READY);
			//FWRecorder.stopObservingLevel();
			console.log("main.js: case stopped??");
			window.enablePlayButton();
			break;

		case "playing_paused":
			name = arguments[1];
			$controls = controlsEl(name);
			setControlsClass($controls, CLASS_PLAYBACK_PAUSED);
			//FWRecorder.stopObservingLevel();
			console.log("main.js: case playing paused");
			window.enablePlayAnchor();
			break;

		case "save_pressed":
			FWRecorder.updateForm();
			console.log("main.js: case save pressed");
			break;

		case "saving":
			name = arguments[1];
			console.log("main.js: case saving");
			break;

		case "saved":
			name = arguments[1];
			var data = $.parseJSON(arguments[2]);
			console.log("main.js: case saved");
			/*
			 * if (data.saved) { $('#upload_status').css({'color':
			 * '#0F0'}).text(name + " was saved"); } else {
			 * $('#upload_status').css({'color': '#F00'}).text(name + " was not
			 * saved"); }
			 */
			break;

		case "save_failed":
			name = arguments[1];
			var errorMessage = arguments[2];
			console.log("main.js: save failed");
			// $uploadStatus.css({'color': '#F00'}).text(name + " failed: " +
			// errorMessage);
			break;

		case "save_progress":
			name = arguments[1];
			var bytesLoaded = arguments[2];
			var bytesTotal = arguments[3];
			console.log("main.js: case save progress");
			// $uploadStatus.css({'color': '#000'}).text(name + " progress: " +
			// bytesLoaded + " / " + bytesTotal);
			break;
		}
	};

	// Helper functions
	// ---------------------------------------------------------------------------------------------------
	window.blockPage = function() {
		$.blockUI();
		$('.blockOverlay').click(function() {
			$.unblockUI({
				onUnblock : function() {
					window.stopAudioSample();
				}
			});
		});
	}

	window.unblockPage = function() {
		$.unblockUI();
	}

	//methods for pretty check box
	window.initiateCheckbox = function(){
		//$('.myClass').prettyCheckable();
		var inputList = document.getElementsByClassName("myClass");
		for (var i = inputList.length - 1; i >= 0; i--) {
		    $(inputList[i]).prettyCheckable();
		}
		$('#answer').prettyCheckable('check');
		$('#answer').prettyCheckable('disable');
		$('#second').prettyCheckable('uncheck');
		$('#second').prettyCheckable('disable');
	}
	
	//progress bar splitted
	window.initiateSplittedProgress = function(splitCount) {
		$("#SplittedProgressBar").width("700px");
		var settings={
			    split: parseFloat(splitCount)
			    };
		$("#SplittedProgressBar").progressBar(settings);
		// $("#SplittedProgressBar").changePercent(10);
	}

	window.makeProgressAtSplittedBar = function(percentage) {
		$("#SplittedProgressBar").changePercent(percentage);
	}

	var mic_test_circle;
	window.initiateMicrophoneTestTimer = function(duration) {
		mic_test_circle = new ProgressBar.Circle('#mic_test_timer_circle', {
			color : '#F00000',
			strokeWidth : 3,
			trailWidth : 1,
			duration : parseInt(duration),
			text : {
				value : '0'
			},
			step : function(state, bar) {
				bar.setText((bar.value() * 100).toFixed(0));
			}
		});

		//circle.animate(1.0); // Number from 0.0 to 1.0

	}
	window.animateMicrophoneTestTimer = function() {
		//ProgressBar.Circle('#mic_test_timer_circle').animate(1,0);
		mic_test_circle.animate(1);
	}
	
	window.animateCircleTimer = function(duration) {
		var circle = new ProgressBar.Circle('#timer_circle', {
			color : '#F00000',
			strokeWidth : 5,
			trailWidth : 2,
			duration : parseInt(duration),
			text : {
				value : '0'
			},
			step : function(state, bar) {
				bar.setText((bar.value() * 100).toFixed(0));
			}
		});

		circle.animate(1.0); // Number from 0.0 to 1.0

	}

	window.animateLineTimer = function(duration) {
		console.log("this is the ultimate duration:" + parseInt(duration));
		var line = new ProgressBar.Line('#timer_line', {
			color : '#F00000',
			strokeWidth : 2,
			trailColor : '#E0E0E0',
			trailWidth : 2,
			duration : parseInt(duration * 1000)
		});

		line.animate(1.0); // Number from 0.0 to 1.0

	}

	window.colorMarkedText = function(textToColor) {
		function myFunc(textToColor) {
			var text = $("#marked_text").html().replace(textToColor,
					'<span class="red">' + textToColor + '</span>');
			$("#marked_text").html(text);

		}
		myFunc(textToColor);
	}

	window.uploadTest = function(name) {
		console.log("this method has been called");
		var blob = FWRecorder.getBlob(name);
		console.log("size:" + blob.size + ", type:" + blob.type);
		var formData = new FormData();
		var filename = name.replace(/\s/g, "") + ".wav";
		formData.append("file", blob, filename);
		$
				.ajax({
					// url:
					// 'http://m-lab.cse.nd.edu:8080/fileupload/rest/files/upload/',
					// //Server script to process data
					url : 'http://10.32.10.188:8080/phrService-0.0.1-SNAPSHOT/files/upload/',
					type : 'post',
					contentType : 'multipart/form-data',
					// content: 'text/html',
					xhr : function() { // Custom XMLHttpRequest
						var myXhr = $.ajaxSettings.xhr();
						if (myXhr.upload) { // Check if upload property exists
							myXhr.upload.addEventListener('progress',
									progressHandlingFunction, false); // For
																		// handling
																		// the
																		// progress
																		// of
																		// the
																		// upload
						}
						return myXhr;
					},
					// Ajax events
					beforeSend : beforeSendHandler,
					success : function() {
						console.log("file upload done....");
					},
					error : function() {
						console.log("text status : " + textStatus);

					}, // Form data
					data : formData,
					// Options to tell jQuery not to process data or worry about
					// content-type.
					cache : false,
					contentType : false,
					processData : false
				});

		function beforeSendHandler() {
			console.log("let see if it calls before sending");
		}

		function progressHandlingFunction(e) {
			// if(e.lengthComputable){
			// $('progress').attr({value:e.loaded,max:e.total});
			// console.log("progress : "+ {value:e.loaded,max:e.total});
			console.log("making some progress");
			// }
		}

	}

	function setUpFormOptions() {
		console.log("main.js: setup form options");
		/*
		 * var gain = $('#gain')[0]; var silenceLevel = $('#silenceLevel')[0];
		 * for (var i = 0; i <= 100; i++) { gain.options[gain.options.length] =
		 * new Option(100 - i);
		 * silenceLevel.options[silenceLevel.options.length] = new Option(i); }
		 */
	}

	function setControlsClass($controls, className) {
		console.log("main.js: set control class");
		$controls.attr('class', CLASS_CONTROLS + ' ' + className);
	}

	function controlsEl(name) {
		console.log("main.js: control s e1");
		return $('#recorder-' + name);
	}

	function recorderEl() {
		console.log("main.js: recorder e1");
		return $('#recorderApp');
	}

	// Button actions
	// -----------------------------------------------------------------------------------------------------

	window.microphonePermission = function() {
		console.log("main.js: microphone permission");
		recorderEl().addClass("floating");
		FWRecorder.showPermissionWindow({
			permanent : true
		});
	};

	window.configureMicrophone = function() {
		if (!FWRecorder.isReady) {
			console.log("main.js: recorder not ready at configure");
			return;
		}
		console.log("main.js: configure microphone");
		FWRecorder.configure($('#rate').val(), $('#gain').val(), $(
				'#silenceLevel').val(), $('#silenceTimeout').val());
		FWRecorder.setUseEchoSuppression($('#useEchoSuppression')
				.is(":checked"));
		FWRecorder.setLoopBack($('#loopBack').is(":checked"));
	};

});
