/***使用方法，网页正文中一定要有一个id='results'的元素************************************************/
/***浮动窗口***********************************/
var plugin="<div id='dd' style='position:absolute;text-align:center;z-index:1;'>";
plugin += "<span style='font-size:24px;color:#C09;font-family:Times'>Draw A Mol</span><br/> ";
plugin += "<div id='appletContainer'></div><br/>";
plugin += "<button type='button' id='search' onclick='Search();'>Search</button></div>";
plugin += "<script  src='jsmol/jsme/jsme/jsme.nocache.js'></script>";
document.write(plugin);
//画图插件相关代码
		var jsmeInstance;
		//Init JSME
		function jsmeOnLoad(){
			jsmeInstance = new JSApplet.JSME("appletContainer", "380px", "340px", {"options" :"query,hydrogens"});
		}
		
		function Search(){
			//param=jsmeInstance.molFile();
			param=jsmeInstance.smiles();
			alert("You are Searching your molecue with SMI:\n\t"+param);
			$.post("SearchStruc",{container:param},function(result){
		
				$("#results").empty(); 
				$("#results").append(result);
			});
		}
//控制浮动窗口相关代码	
var delay = 30; 
var pause = true;
var interval;
function changePos() {
	dd.style.left = document.body.clientWidth - 380;
	//dd.style.top =document.body.scrollTop +300 ;
}

function start() {
	dd.visibility = "visible";
	interval = setInterval('changePos()', delay);
}
start(); 

