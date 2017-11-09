/**
 * 
 */


(function(global) {
  var Recorder;

  var RECORDED_AUDIO_TYPE = "audio/wav";
  
  console.log("recorder.js loaded");

  Recorder = {
		  
	recordRTC:null,
	fileName:null,
	
	captureMicrophone: function(){
		
		try{
	        navigator.getUserMedia = navigator.getUserMedia || navigator.mozGetUserMedia || navigator.webkitGetUserMedia;
	        navigator.getUserMedia({audio: true}, 
	        	function(stream){        	
	        		recordRTC = RecordRTC(stream, {
	                    type: 'audio',
	                    recorderType: StereoAudioRecorder,
	                    desiredSampRate: 44100
	                });
	        		window.updateMirophonePermission(true);

	        	}, function(error) {
//	        		alert('Unable to access your microphone.');
//	        		console.log("unable to access your microphone "+error);
	        		window.updateMirophonePermission(false);
	        	});					
		}catch(err){
			window.updateMirophoneDetection();
		}
	},
	
    captureMicrophoneToRecord: function(name, pauseImmediately) {
        navigator.getUserMedia = navigator.getUserMedia || navigator.mozGetUserMedia || navigator.webkitGetUserMedia;
        navigator.getUserMedia({audio: true}, 
        	function(stream){
        		window.updateMirophonePermission(true);
        		fileName = name;
        		console.log("suuceess call back..."+ fileName + ", pause:"+ pauseImmediately);
        	
        		recordRTC = RecordRTC(stream, {
                    type: 'audio',
                    recorderType: StereoAudioRecorder,
                    desiredSampRate: 44100
                });
//        		recordRTC.clearRecordedData();
        		recordRTC.startRecording();
        		if(pauseImmediately){
        			recordRTC.pauseRecording();
        		}
        		
        	}, function(error) {
        		alert('Unable to access your microphone.');
        		console.error(error);
        		window.updateMirophonePermission(false);
        	});
    },
    startRecording: function(name, pauseImmediately=true){
    	console.log("name:"+ name + " pause imm"+ pauseImmediately);
    	console.log("record rtc" + typeof(recordRTC));
    	if(typeof(recordRTC) !== "undefined" && recordRTC){
    		console.log("record rtc is not null, so can we start right now");
    		recordRTC.clearRecordedData();
    		recordRTC.startRecording();
    		if(pauseImmediately){
    			recordRTC.pauseRecording();
    		}
    	}else{
    		console.log("need to initialize record rtc");
    		Recorder.captureMicrophoneToRecord(name, pauseImmediately);
    	}
    	
    },
    stopRecording: function() {
    	try{
    		console.log("going to stop....from js, recorder state:"+ recordRTC.state);
            recordRTC.stopRecording(function(){
            	try{
            		window.enableRecordingEnding();

            		if(window.location.hostname === "127.0.0.1" || window.location.hostname === "localhost"){
            			this.save("test.wav");
            		}else{
            			console.log("hostname:"+ window.location.hostname);
            		}
            	} catch(error){
            		console.log("no function to call visible upload");
            	}
            	
                //var blob = recordRTC.getBlob();
                //var audio = document.createElement('audio');
                //audio.src = 'js/1to30.mp3';
                //audio.mozSrcObject = blob;
                //audio.src = recordRTC.toURL();
                //audio.play();
        		console.log("recorder state after stop:"+ recordRTC.state + ", localhost:" + window.location.hostname);
            });
            
    	} catch(err){
    		console.log("in stop:"+ err);
    	}
    }, 
    pauseRecording: function(){
    	try{
        	if(recordRTC !== null){
            	recordRTC.pauseRecording();    		
        	}else{
        		console.log("record rtc is null");
        	}
    		
    	}catch(err){
    		console.log("error in pause :"+ err);
    	}
    },
    resumeRecording: function(){
    	try{
        	if(recordRTC !== null){
            	recordRTC.resumeRecording();    		
        	}else{
        		console.log("record rtc is null");
        	}  		
    	} catch(err){
    		console.log("error in resume :"+ err);
    	}
    },
    getBlobData: function(){
    	return recordRTC.getBlob();
    },
    getFileName: function(){
    	return fileName;
    },
    save: function(path){
    	var fullPath = path + fileName;
    	console.log("save path:"+ fullPath);
    	recordRTC.save(fullPath);
    }

  };



  global.Html5Recorder = Recorder;


})(this);