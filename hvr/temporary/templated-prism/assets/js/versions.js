//Determines OS, Browswer, and Browser Version
if (navigator.appVersion.indexOf("Win")!=-1) OSName="Windows";
else if (navigator.appVersion.indexOf("Mac")!=-1) OSName="Mac";
else if (navigator.appVersion.indexOf("X11")!=-1) OSName="UNIX";
else if (navigator.appVersion.indexOf("Linux")!=-1) OSName="Linux";
else OSName="Unknown Operating System";

var N= navigator.appName, ua= navigator.userAgent, tem;
var M= ua.match(/(opera|chrome|safari|firefox|msie|trident)\/?\s*(\.?\d+(\.\d+)*)/i);
    if(M && (tem= ua.match(/version\/([\.\d]+)/i))!= null) {M[2]=tem[1];}
    M= M? [M[1], M[2]]: [N, navigator.appVersion,'-?'];

document.write('Your OS: '+OSName + '<br>' +
'Your Browser: '+M[0] + '<br>' +
'Browser Version: ' + M[1]);

