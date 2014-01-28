<%@ taglib uri="http://jawr.net/tags" prefix="jwr" %>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html>
<html>
	<head>
		<title>${applicationScope.appname}</title>

		<meta name="viewport" content="width=device-width, initial-scale=1.0"/>

		<jwr:style src="/bundles/sslib.css" media="screen"/>
		<jwr:style src="/bundles/ssapp.css" media="screen"/>
		<jwr:script src="/bundles/jslib.js"/>
		<jwr:script src="/bundles/jsapp.js"/>
	</head>
	<body ng-module="app">
		<div ng-view></div>
	</body>
</html>