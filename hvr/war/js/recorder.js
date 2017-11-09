
(function(global) {
  var Recorder;

  var RECORDED_AUDIO_TYPE = "audio/wav";

  Recorder = {
    recorder: null,
    recorderOriginalWidth: 0,
    recorderOriginalHeight: 0,
    uploadFormId: null,
    uploadFieldName: null,
    isReady: false,

    connect: function(name, attempts) {
      if(navigator.appName.indexOf("Microsoft") != -1) {
        Recorder.recorder = window[name];
      } else {
        Recorder.recorder = document[name];
      }

      if(attempts >= 40) {
        return;
      }

      // flash app needs time to load and initialize
      if(Recorder.recorder && Recorder.recorder.init) {
        Recorder.recorderOriginalWidth = Recorder.recorder.width;
        Recorder.recorderOriginalHeight = Recorder.recorder.height;
        try {
            if(Recorder.uploadFormId && $) {
                var frm = $(Recorder.uploadFormId); 
                Recorder.recorder.init(frm.attr('action').toString(), Recorder.uploadFieldName, frm.serializeArray());
              }
            console.log("probably init completed ");
		} catch (e) {
			// TODO: handle exception
			console.log("exception at init : "+ e.message);
		}
        return;
      }

      setTimeout(function() {Recorder.connect(name, attempts+1);}, 100);
    },

    playBack: function(name) {
      // TODO: Rename to `playback`
    console.log("recorder.js: will start playback");
      Recorder.recorder.playBack(name);
    },

    pausePlayBack: function(name) {
      // TODO: Rename to `pausePlayback`
    console.log("recorder.js: pause playback");
      Recorder.recorder.pausePlayBack(name);
    },
    
    playBackFrom: function(name, time) {
      // TODO: Rename to `playbackFrom`
    	console.log("recorder.js: playback from - probably after pause");
      Recorder.recorder.playBackFrom(name, time);
    },

    record: function(name, filename) {
      Recorder.recorder.record(name, filename);
      Recorder.recorder.hide();
  	  console.log("recorder.js: possible function to start recording");
    },
    
    stopRecording: function() {
    	console.log("recorder.js: stop recording");
      Recorder.recorder.stopRecording();
    },

    stopPlayBack: function() {
      // TODO: Rename to `stopPlayback`
    	console.log("recorder.js: stop playback");
      Recorder.recorder.stopPlayBack();
    },

    observeLevel: function() {
    	console.log("recorder.js: observe level");
      Recorder.recorder.observeLevel();
    },

    stopObservingLevel: function() {
    	console.log("recorder.js: stop observe level");
      Recorder.recorder.stopObservingLevel();
    },

    observeSamples: function() {
    	console.log("recorder.js: observe samples");
      Recorder.recorder.observeSamples();
    },

    stopObservingSamples: function() {
    	console.log("recorder.js: stop observe samples");
      Recorder.recorder.stopObservingSamples();
    },

    resize: function(width, height) {
    	console.log("recorder.js: resize width & height");

      Recorder.recorder.width = width + "px";
      Recorder.recorder.height = height + "px";
    },

    defaultSize: function() {
      Recorder.resize(Recorder.recorderOriginalWidth, Recorder.recorderOriginalHeight);
    },

    show: function() {
    	console.log("recorder.js: show recorder");
      Recorder.recorder.show();
    },

    hide: function() {
    	console.log("recorder.js: hide recorder");
    	try {
    		//$('#recorderApp').parent().append($('#recorderApp'));
    		$('#recorderApp').css('z-index', 9999);
    		$("#recorderApp").css('top', -150);
    		$("#recorderApp").css('left', -950);
    		//var position = $('#recorderApp').position();
    		
  			//var position = recorderEl().Position();
  			//console.log("print position of current window:"+ position.left + ", " + position.top);
			
		} catch (e) {
			// TODO: handle exception
			console.log("exception occured : "+ e.message);
		}
      Recorder.recorder.hide();
    },

    duration: function(name) {
      // TODO: rename to `getDuration`
      return Recorder.recorder.duration(name || Recorder.uploadFieldName);
    },

    getBase64: function(name) {
      var data = Recorder.recorder.getBase64(name);
      return 'data:' + RECORDED_AUDIO_TYPE + ';base64,' + data;
    },

    getBlob: function(name) {
      var base64Data = Recorder.getBase64(name).split(',')[1];
      return base64toBlob(base64Data, RECORDED_AUDIO_TYPE);
    },

    getCurrentTime: function(name) {
    	return Recorder.recorder.getCurrentTime(name);
    },

    isMicrophoneAccessible: function() {
    	try{
        	console.log("recorder.js: going to check if microphone accesible");
            return Recorder.recorder.isMicrophoneAccessible();
    		
    	} catch(error){
    		console.log("exception microphone access:"+ error.message);
    	}
    },

    updateForm: function() {
      var frm = $(Recorder.uploadFormId); 
      Recorder.recorder.update(frm.serializeArray());
    },

    showPermissionWindow: function(options) {
    	console.log("recorder.js: show permission window");
    	Recorder.resize(240, 160);
    	try {
    		//$('#recorderApp').parent().append($('#recorderApp'));
    		$('#recorderApp').css('z-index', 9999);
//    		$("#recorderApp").css('top', 150);
//    		$("#recorderApp").css('left', 750);
    		//var position = $('#recorderApp').position();
    		
  			//var position = recorderEl().Position();
  			//console.log("print position of current window:"+ position.left + ", " + position.top);
			
		} catch (e) {
			// TODO: handle exception
			console.log("exception occured : "+ e.message);
		}
  		
      // need to wait until app is resized before displaying permissions screen
      var permissionCommand = function() {
        if (options && options.permanent) {
          Recorder.recorder.permitPermanently();
        } else {
          Recorder.recorder.permit();
        }
      };
      setTimeout(permissionCommand, 1);
    },

    configure: function(rate, gain, silenceLevel, silenceTimeout) {
      rate = parseInt(rate || 22);
      gain = parseInt(gain || 100);
      silenceLevel = parseInt(silenceLevel || 0);
      silenceTimeout = parseInt(silenceTimeout || 4000);
      switch(rate) {
      case 44:
      case 22:
      case 11:
      case 8:
      case 5:
        break;
      default:
        throw("invalid rate " + rate);
      }

      if(gain < 0 || gain > 100) {
        throw("invalid gain " + gain);
      }

      if(silenceLevel < 0 || silenceLevel > 100) {
        throw("invalid silenceLevel " + silenceLevel);
      }

      if(silenceTimeout < -1) {
        throw("invalid silenceTimeout " + silenceTimeout);
      }

      Recorder.recorder.configure(rate, gain, silenceLevel, silenceTimeout);
    },

    setUseEchoSuppression: function(val) {
      if(typeof(val) != 'boolean') {
        throw("invalid value for setting echo suppression, val: " + val);
      }

      Recorder.recorder.setUseEchoSuppression(val);
    },

    setLoopBack: function(val) {
      if(typeof(val) != 'boolean') {
        throw("invalid value for setting loop back, val: " + val);
      }

      Recorder.recorder.setLoopBack(val);
    }
    

  };

  function base64toBlob(b64Data, contentType, sliceSize) {
    contentType = contentType || '';
    sliceSize = sliceSize || 512;

    var byteCharacters = atob(b64Data);
    var byteArrays = [];

    for (var offset = 0; offset < byteCharacters.length; offset += sliceSize) {
      var slice = byteCharacters.slice(offset, offset + sliceSize);

      var byteNumbers = new Array(slice.length);
      for (var i = 0; i < slice.length; i++) {
        byteNumbers[i] = slice.charCodeAt(i);
      }

      var byteArray = new Uint8Array(byteNumbers);
      byteArrays.push(byteArray);
    }

    return new Blob(byteArrays, {type: contentType});
  }

  global.FWRecorder = Recorder;


})(this);