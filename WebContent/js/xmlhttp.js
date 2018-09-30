function send_request(callback, urladdress, isReturnData){      
        var xmlhttp = getXMLHttpRequest();
        xmlhttp.onreadystatechange = function(){
            	if (xmlhttp.readyState == 4) {//readystate is 4, transfer data is over
 				    try{
				    	if(xmlhttp.status == 200){
							if(isReturnData && isReturnData==true){
								callback(xmlhttp.responseText);
							}
						}else{
							callback("Sorry, not find this page: "+ urladdress +"");
						}
			        } catch(e){
			        	callback("Sorry, send request fail, please try again!" + e);
			        }
			   }
        }
        xmlhttp.open("GET", urladdress, true);
        xmlhttp.send(null);
}

function getXMLHttpRequest() {
        var xmlhttp;
		if (window.XMLHttpRequest) {
			try {
				xmlhttp = new XMLHttpRequest();
				xmlhttp.overrideMimeType("text/html;charset=UTF-8");//recognize data due to UTF-8
			} catch (e) {}
		} else if (window.ActiveXObject) {
			try {
				xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
			} catch (e) {
				try {
					xmlhttp = new ActiveXObject("Msxml2.XMLHttp");
				} catch (e) {
					try {
						xmlhttp = new ActiveXObject("Msxml3.XMLHttp");
					} catch (e) {}
				}
			}
		}
        return xmlhttp;
}
