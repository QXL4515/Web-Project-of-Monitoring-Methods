<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript" src="../js/jquery.min.js"></script>
  <script type="text/javascript" src="../js/highcharts.js"></script>
  <script type="text/javascript" src="../js/exporting.js"></script>
  
<script type="text/javascript">
//$(document).ready(function(){
var data1;
var data2;

$(function () {
    $('#container').highcharts({
        title: {
            text: 'Monthly Average Temperature',
            x: -20 //center
        },
        subtitle: {
            text: 'Source: WorldClimate.com',
            x: -20
        },
        xAxis: {
            categories: data1
        },
        yAxis: {
            title: {
                text: 'Temperature (°C)'
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }]
        },
        tooltip: {
            valueSuffix: '°C'
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle',
            borderWidth: 0
        },
        series: data2
        
    });
});
var url;
var ids;
function sprt(){
	
	var url = "/Test/data?action=sprt";
	alert(url);

	    $.ajax({  
	        type:"post",//请求方式  
	        url:url,//发送请求地址  
	        dataType:"json",
	        timeout : 10000000000, //超时时间设置，单位毫秒
	        //data:{//发送给数据库的数据  
	        //},  
	        //请求成功后的回调函数有两个参数  
	        success:function(data,textStatus){  
	        	
	            data1 = data.jsonx;
	            data2 = data.jsony;

	            alert(eval(data2));
	            
	            $('#container').highcharts({
	                title: {
	                    text: 'Monthly Average Temperature',
	                    x: -20 //center
	                },
	                subtitle: {
	                    text: 'Source: WorldClimate.com',
	                    x: -20
	                },
	                xAxis: {
	                    categories: data1
	                },
	                yAxis: {
	                    title: {
	                        text: 'Temperature (°C)'
	                    },
	                    plotLines: [{
	                        value: 0,
	                        width: 1,
	                        color: '#808080'
	                    }]
	                },
	                tooltip: {
	                    valueSuffix: '°C'
	                },
	                legend: {
	                    layout: 'vertical',
	                    align: 'right',
	                    verticalAlign: 'middle',
	                    borderWidth: 0
	                },
	                series: data2
	                
	            });
	        }  
	    });  
	 
	

}

function ibsrm(){
	
	var url = "http://127.0.0.1:8080/Test/data?action=ibsrm";
	alert(url);

	    $.ajax({  
	        type:"post",//请求方式  
	        url:url,//发送请求地址  
	        dataType:"json",
	        timeout : 10000000000, //超时时间设置，单位毫秒
	        
	        success:function(data,textStatus){  
	        	
	            data1 = data.jsonx;
	            data2 = data.jsony;

	            alert(eval(data1));
	            
	            $('#container').highcharts({
	                title: {
	                    text: 'Monthly Average Temperature',
	                    x: -20 //center
	                },
	                subtitle: {
	                    text: 'Source: WorldClimate.com',
	                    x: -20
	                },
	                xAxis: {
	                    categories: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20]
	                },
	                yAxis: {
	                    title: {
	                        text: 'Temperature (°C)'
	                    },
	                    plotLines: [{
	                        value: 0,
	                        width: 1,
	                        color: '#808080'
	                    }]
	                },
	                tooltip: {
	                    valueSuffix: '°C'
	                },
	                legend: {
	                    layout: 'vertical',
	                    align: 'right',
	                    verticalAlign: 'middle',
	                    borderWidth: 0
	                },
	                series: data2
	                
	            });
	        }  
	    });  
	 
	


}


function wbsrm(){
	
	var url = "http://127.0.0.1:8080/Test/data?action=wbsrm";
	alert(url);

	    $.ajax({  
	        type:"post",//请求方式  
	        url:url,//发送请求地址  
	        dataType:"json",
	        timeout : 10000000000, //超时时间设置，单位毫秒
	        //data:{//发送给数据库的数据  
	        //},  
	        //请求成功后的回调函数有两个参数  
	        success:function(data,textStatus){  
	        	
	            data1 = data.jsonx;
	            data2 = data.jsony;

	            alert(eval(data1));
	            
	            $('#container').highcharts({
	                title: {
	                    text: 'Monthly Average Temperature',
	                    x: -20 //center
	                },
	                subtitle: {
	                    text: 'Source: WorldClimate.com',
	                    x: -20
	                },
	                xAxis: {
	                    categories: data1
	                },
	                yAxis: {
	                    title: {
	                        text: 'Temperature (°C)'
	                    },
	                    plotLines: [{
	                        value: 0,
	                        width: 1,
	                        color: '#808080'
	                    }]
	                },
	                tooltip: {
	                    valueSuffix: '°C'
	                },
	                legend: {
	                    layout: 'vertical',
	                    align: 'right',
	                    verticalAlign: 'middle',
	                    borderWidth: 0
	                },
	                series: data2
	               
	            });
	        }  
	    });  
	 
	

}

function srm1(){
	
	var url = "http://127.0.0.1:8080/Test/data?action=1";
	alert(url);

	    $.ajax({  
	        type:"post",//请求方式  
	        url:url,//发送请求地址  
	        dataType:"json",
	        timeout : 10000000000, //超时时间设置，单位毫秒
	        //data:{//发送给数据库的数据  
	        //},  
	        //请求成功后的回调函数有两个参数  
	        success:function(data,textStatus){  
	        	
	            data1 = data.jsonx;
	            data2 = data.jsony;

	            alert(eval(data1));
	            
	            $('#container').highcharts({
	                title: {
	                    text: 'Monthly Average Temperature',
	                    x: -20 //center
	                },
	                subtitle: {
	                    text: 'Source: WorldClimate.com',
	                    x: -20
	                },
	                xAxis: {
	                    categories: data1
	                },
	                yAxis: {
	                    title: {
	                        text: 'Temperature (°C)'
	                    },
	                    plotLines: [{
	                        value: 0,
	                        width: 1,
	                        color: '#808080'
	                    }]
	                },
	                tooltip: {
	                    valueSuffix: '°C'
	                },
	                legend: {
	                    layout: 'vertical',
	                    align: 'right',
	                    verticalAlign: 'middle',
	                    borderWidth: 0
	                },
	                series: data2
	                	/*[{
	                    name: 'Tokyo',
	                    data: data2
	                } , {
	                    name: 'New York',
	                    data: [-0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5]
	                }, {
	                    name: 'Berlin',
	                    data: [-0.9, 0.6, 3.5, 8.4, 13.5, 17.0, 18.6, 17.9, 14.3, 9.0, 3.9, 1.0]
	                }, {
	                    name: 'London',
	                    data: [3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8]
	                } ]*/
	            });
	        }  
	    });  
	 
	
//	$.post("http://127.0.0.1:8080/Test/data?action=sprt&rtPath="+$("#rtPath").val()+"&tpPath="+$("#thPath")+"&userPath="+
//			$("#userPath")+"&wsPath="+$("#wsPath"));
	

}

function srm2(){
	
	var url = "http://127.0.0.1:8080/Test/data?action=2";
	alert(url);

	    $.ajax({  
	        type:"post",//请求方式  
	        url:url,//发送请求地址  
	        dataType:"json",
	        timeout : 10000000000, //超时时间设置，单位毫秒
	        //data:{//发送给数据库的数据  
	        //},  
	        //请求成功后的回调函数有两个参数  
	        success:function(data,textStatus){  
	        	
	            data1 = data.jsonx;
	            data2 = data.jsony;

	            alert(eval(data1));
	            
	            $('#container').highcharts({
	                title: {
	                    text: 'Monthly Average Temperature',
	                    x: -20 //center
	                },
	                subtitle: {
	                    text: 'Source: WorldClimate.com',
	                    x: -20
	                },
	                xAxis: {
	                    categories: data1
	                },
	                yAxis: {
	                    title: {
	                        text: 'Temperature (°C)'
	                    },
	                    plotLines: [{
	                        value: 0,
	                        width: 1,
	                        color: '#808080'
	                    }]
	                },
	                tooltip: {
	                    valueSuffix: '°C'
	                },
	                legend: {
	                    layout: 'vertical',
	                    align: 'right',
	                    verticalAlign: 'middle',
	                    borderWidth: 0
	                },
	                series: data2
	                	/*[{
	                    name: 'Tokyo',
	                    data: data2
	                } , {
	                    name: 'New York',
	                    data: [-0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5]
	                }, {
	                    name: 'Berlin',
	                    data: [-0.9, 0.6, 3.5, 8.4, 13.5, 17.0, 18.6, 17.9, 14.3, 9.0, 3.9, 1.0]
	                }, {
	                    name: 'London',
	                    data: [3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8]
	                } ]*/
	            });
	        }  
	    });  
	 
	
//	$.post("http://127.0.0.1:8080/Test/data?action=sprt&rtPath="+$("#rtPath").val()+"&tpPath="+$("#thPath")+"&userPath="+
//			$("#userPath")+"&wsPath="+$("#wsPath"));
	

}
</script>

</head>
<body>
<span><font color="red">${sign }</font></span>
<span><font color="red">${error }</font></span>
<div>
<input type="file" name="rtPath" id="rtPath">
<input type="file" name="tpPath" id="tpPath">
<input type="file" name="userPath" id="userPath">
<input type="file" name="wsPath" id="wsPath">
</div>
<div style="margin-top:10px">
<button type="button" id="sprt" onclick="sprt()">SPRT</button>
<button type="button" id="ibsrm" onclick="ibsrm()">IBSRM</button>
<button type="button" id="wbsrm" onclick="wbsrm()">WBSRM</button>
<button type="button" id="ibsrm" onclick="srm1()">1</button>
<button type="button" id="wbsrm" onclick="srm2()">2</button>
</div>
<div id="container" style="min-width:400px;height:400px"></div>
</body>
</html>