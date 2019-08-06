<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page isELIgnored="false"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<style type="text/css">
body {
	background: url("images/bg.jpg");
	background-repeat: no-repeat;
	background-size: cover;
	background-position-x: 50%;
}
</style>
</style>

<!-- meta -->
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<meta name="Title" content="iPPIDB" />
<meta name="Author" content="C. LabbÃÂ©" />
<meta name="Subject" content="Site iPPIDB" />
<meta name="Description"
	content="Inhibitors of Protein-Protein Interaction Database" />
<meta name="Language" content="en" />
<meta name="Expires" content="" />
<meta name="Robots" content="All" />
<meta name="copyright" content="MTi" />
<meta name="viewport"
	content="user-scalable=no, width=device-width, initial-scale=1.0, maximum-scale=1.0" />
<!-- end meta -->

<!-- title -->
<title>KBQA</title>
<!-- end title -->

<!-- favico -->
<link rel="shortcut icon"
	href="http://www.ippidb.cdithem.fr/favicon.ico" />
<!-- end favico -->

<!-- feuilles css -->
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/dg-picture-zoom.css" type="text/css" />
<link rel="stylesheet" href="css/jquery.dataTables.css" type="text/css" />
<link rel="stylesheet" href="css/ippidb.css">
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />


<script type="text/javascript" src="js/jwplayer/jwplayer.js"></script>
<script type="text/javascript">jwplayer.key="bWWTf/3ccN7g3kL7rstRl/c0akS5c18agoNzvg==";</script>
<!--<script type="text/javascript" src="http://www.ippidb.cdithem.fr/js/jquery-1.7.2.js"></script>-->
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<script type="text/javascript" src="js/bootstrap.js"></script>
<script type="text/javascript" src="js/jquery.dataTables.js"></script>

<script type="text/javascript" src="js/paging.js"></script>
<script type="text/javascript"
	src="js/external/mootools-1.2.4-core-yc.js"></script>
<script type="text/javascript" src="js/external/mootools-more.js"></script>
<script type="text/javascript" src="js/Chart.js"></script>
<!--<script type="text/javascript" src="http://www.ippidb.cdithem.fr/js/Chart_HorizontalBar.js"></script>-->
<script type="text/javascript" src="js/dg-picture-zoom.js"></script>
<script type="text/javascript" src="js/dg-picture-zoom-autoload.js"></script>



<script type='text/javascript'>
  			$(document).ready(
				function(){
					$('#loader').show();				
	 				
				}
			);
			$(window).load(function() {
				// On fait disparaitre notre page de chargement
			
				$("#loader").fadeOut(function(ui, event) {
					//Ensuite on fait apparaitre notre contenu
					$("#centre_contenu").fadeIn(900);
					ttInstances = TableTools.fnGetMasters();
					for (i in ttInstances) {
						if (ttInstances[i].fnResizeRequired()) ttInstances[i].fnResizeButtons();
					
					}
				});
			});

  	</script>
<script type='text/javascript'>
     		$(document).ready(function () 
     		{
     			if ($("[rel=tooltip]").length) 
     			{
     				$("[rel=tooltip]").tooltip();
     			}
   			});
  	</script>

<script type='text/javascript'>
     		$(function () {
  $('[data-toggle="popover"]').popover()
})  	</script>

<script type='text/javascript'>
  			$(function (){
    			$('.carousel').carousel({
  					interval: 2200
				});    			
			});
	</script>

<script>
			//sets up numeric sorting of links
		jQuery.fn.dataTableExt.oSort['num-html-asc']  = function(a,b) {
		    var x = a.replace( /<.*?>/g, "" );
		    var y = b.replace( /<.*?>/g, "" );
		    x = parseFloat( x );
		    y = parseFloat( y );
		    return ((x < y || isNaN(y) ) ? -1 : ((x > y || isNaN(x)) ?  1 : 0));
		};
 
		jQuery.fn.dataTableExt.oSort['num-html-desc'] = function(a,b) {
		    var x = a.replace( /<.*?>/g, "" );
		    var y = b.replace( /<.*?>/g, "" );
		    x = parseFloat( x );
		    y = parseFloat( y );
		    return ((x < y || isNaN(x)) ?  1 : ((x > y || isNaN(y) ) ? -1 : 0));
		};

	     	$(document).ready(function() {
		    	$('#resultsDrugOnly').dataTable( {
		    		"sDom": "<'row'>iprt<'row'>ip",
		    		"sPaginationType": "bootstrap",
		    		"bProcessing": true,
		    		"aaSorting": [],		    		
		    		"aoColumnDefs": [{ "bSortable": false, "aTargets": [ 1 ] },{ "bSortable": false, "aTargets": [ 3 ] },{ "bSortable": false, "aTargets": [ 4 ] },{ "bSortable": false, "aTargets": [ 5 ] },{ "sType": "num-html", "aTargets": [ 0 ] },{ "sType": "num-html", "aTargets": [ 18 ] } ],
		    		"iDisplayLength": 50
		    				    	});
		    				    	
   			} );
   			
   			$(document).ready(function() {
		    	$('#results').dataTable( {
		    		"sDom": "<'row'>iprt<'row'>ip",
		    		"sPaginationType": "bootstrap",
		    		"bProcessing": true,
		    		"aaSorting": [],		    		
		    		"aoColumnDefs": [{ "bSortable": false, "aTargets": [ 1 ] },{ "bSortable": false, "aTargets": [ 2 ] },{ "bSortable": false, "aTargets": [ 3 ] },{ "bSortable": false, "aTargets": [ 4 ] },{ "sType": "num-html", "aTargets": [ 0 ] },{ "sType": "num-html", "aTargets": [ 17 ] } ],
		    		"iDisplayLength": 50
		    				    	});
		    				    	
   			} );
   			
   			$(document).ready(function() {
		    	$('#resultsSimilarite').dataTable( {
		    		"sDom": "<'row'>iprt<'row'>Fip",
		    		"sPaginationType": "bootstrap",
		    		"bProcessing": true,
		    		"aaSorting": [[ 0, "desc" ]],		    		
		    		"aoColumnDefs": [{ "bSortable": false, "aTargets": [ 2 ] },{ "bSortable": false, "aTargets": [ 3 ] },{ "bSortable": false, "aTargets": [ 4 ] },{ "bSortable": false, "aTargets": [ 5 ] },{ "sType": "num-html", "aTargets": [ 0 ] },{ "sType": "num-html", "aTargets": [ 18 ] } ],
		    		"iDisplayLength": 50,
		    		"oTableTools": { "sSwfPath": "tableTools/media/swf/copy_csv_xls_pdf.swf", "aButtons": [ {"sExtends": "csv","mColumns": [0, 1, 2, 4]}] }
				});
		    				    				    	
   			} );
   				
		</script>


</head>

<body>
	<div class="wrapper">

		<div class="container">
			<div class="row">
				<div class="span11 ssTitre acknowIndex">
					<h1>
						KBQA<br>
					</h1>
					<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Knowledge
						Base Question Answering Based on Deep Learning Models</p>
				</div>
			</div>
			<!-- il reste a fermer le div container, le div wrapper la partie html a partir du body -->
			<div class="row">
				<div class="span5">
					<div class="ssTitre acknowIndex">Question</div>
					<form action="SearchAnswer" method="post" style="padding-top:10px">
						<textarea class="form-control" rows="3"
							style="width: 98%; height: 80px" name="question">${question}</textarea>
						<input type="submit" class="btn btn-primary" style="width:100%;height:30px" value="Submit"/>
					</form>
				</div>
				<!-- fin span6 -->

				<div class="span6">
					<div class="ssTitre acknowIndex">Anwser</div>
					<div class="ssTitre acknowIndex" style="height: 80px;font-size:13px">
						${answer }
						<div id="lienSignUp"></div>
					</div>
				</div>
				<!-- fin span5 offset1 -->
			</div>
			<!-- fin row -->
			<div class="row">
				<div class="span5">
					<div class="ssTitre acknowIndex">Test Questions</div>
					<div class="ssTitre acknowIndex" style="font-size:13px">
						陈刚是哪的人？<br>
						能告诉我《兄弟》这本书是谁写的吗？<br>
						你知道四川艺术职业学院是归谁管吗？<br>
						我想问一下张磊有多高？<br>
						我想知道真相这本书是谁翻译的呀？<br>
						你知道mc的外文名是什么吗？<br>
						李敏的出身地在什么地方？<br>
						全球通史的装帧是什么样子的？<br>
						我想知道神雕侠侣是什么类型的电视剧？<br>
						高等数学是几开的书？<br>
						路虎是哪年成立的？<br>
						你知道冬虫夏草是哪种科吗？<br>
						cid是什么意思？<br>
						长安raeton是什么品牌的呀？<br>
						濽用拼音怎么拼啊？<br>
						有谁知道东芝c600-c31n的屏幕尺寸是多大吗？<br>
						开心宝贝之开心星星球这部动画片是开心宝贝哪个系列的？<br>
					</div>
					<div id="lienSignUp"></div>
				</div>
				<div class="span6">
					<div class="ssTitre acknowIndex">Candidates</div>
					<div class="ssTitre acknowIndex" style="font-size:13px">
						${candidates }
					<div id="lienSignUp"></div>
					</div>
				</div>
			</div>
		</div>
</div>
</body>
</html>