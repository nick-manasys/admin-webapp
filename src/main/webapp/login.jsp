<html>
<head>
<title>Login Page</title>

<link href="resources/css/_styles.css" rel="stylesheet" type="text/css" />
<link href="resources/css/admin-app.css" rel="stylesheet"
	type="text/css" />
<link href="resources/css/jquery-ui-1.9.2.custom.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" charset="utf-8"
	src="resources/js/jquery-1.9.0.js"></script>
<script type="text/javascript" charset="utf-8"
	src="resources/js/jquery-ui-1.9.2.custom.js"></script>
</head>

<script type="text/javascript">
	function submitForm(form) {
		alert('crap');
		//		document.myform.submit();
		return false;
	}

	function submitOne() {
		$("form:first").submit();
		return true;
	}

	function validateFormVars(form) {
		var errors = '';
		if (form.IDToken1.value == '') {
			errors += "Please enter Login ID\n\n"
		}
		if (form.IDToken2.value == '') {
			errors += "Please enter Password\n\n"
		}

		if (errors == '') {
			return true;
		} else {
			alert(errors);
			return false;
		}
	}
</script>

<body>
	<%
	    if ("POST".equalsIgnoreCase(request.getMethod())) {
					response.sendRedirect(session.getServletContext()
							.getContextPath() + "/?login");
				}
	%>
	<div style="width: 100%; margin: 0 auto;">
		<div style="width: 400; float: center; display: inline-block;">
			<form id="loginForm" name="LoginForm" method="POST"
				action="${pageContext.request.contextPath}/j_security_check"
				onSubmit="return validateFormVars(this)">
				<table>
					<tr>
						<td>User:</td>
						<td><input type='text' name='j_username' id="j_username"
							value=''></td>
					</tr>
					<tr>
						<td>Password:</td>
						<td><input type='password' name='j_password' /></td>
					</tr>
					<tr>
						<td><input type='checkbox'
							name='_spring_security_remember_me' /></td>
						<td>Remember me on this computer.</td>
					</tr>
					<tr>
						<td colspan='2' align='center'><input name="submit"
							type="submit" value="Login" class="hidden" id="loginButtonId" />
							<!-- <a class="btnDark"
							onClick="document.getElementById('loginId').submit();"
							style="float: right; margin-top: 3px;" id="add" href="#">Login</a>-->
							<a href="#" class="btnDark"
							style="float: center; margin-top: 3px;"
							onclick="$('#loginButtonId').click(); return true;">Log In</a></td>

					</tr>
					<!-- 
					<tr>
						<td colspan='2' align='center'><a class="btnDark"
							style="float: right; margin-top: 3px;" id="add" href="#"
							onClick="document.getElementById('loginId').submit();"><input
								name="submit" type="submit" value="Submit"
								style="position: absolute; left: -9999px" />Login</a></td>
					</tr>
					<tr>
						<td colspan='2' align='center'><a class="btnDark"
							style="float: right; margin-top: 3px;" id="add" href="#"
							onClick="javascript: submitForm();">Login</a></td>
						</tr>
					 -->
				</table>
			</form>

			<!-- 
			<div>
				<div style="width: 200px; float: center; display: inline-block;">
					<form name="Login"
						action="https://www.dev.example.co.nz/amserver/UI/Login"
						method="post" onSubmit="return validateFormVars(this)">
						<input type="hidden" name="goto" value="/
/listCircuits" /> <input
							type="hidden" name="failUrl"
							value="${pageContext.request.contextPath}/login.jsp?goto=${pageContext.request.contextPath}/listCircuits" />
						<input type="hidden" name="IDToken0" value="" /> <input
							type="hidden" name="realm" value="tclstaff" /> <input
							type="hidden" name="encoded" value="false" /> <input
							type="hidden" name="gx_charset" value="UTF-8" /> <input
							type="hidden" name="service" value="staff" />

						<div
							style="width: 195px; float: center; font-size: 12px; color: #666666; padding-right: 10px;">
							<label for="login">Login ID</label> <input name="IDToken1"
								type="text" tabindex="1" class="pnlBoxInput" id="IDToken1"
								style="width: 190px" onclick="this.value='';" />
						</div>
						<div
							style="width: 195px; float: center; font-size: 12px; color: #666666;">
							<label for="pw">Password</label> <input name="IDToken2"
								type="password" tabindex="2" class="pnlBoxInput" id="IDToken2"
								style="width: 190px" onclick="this.value='';" />
						</div>
						<div class="clear"></div>
						<input tabindex="3" type="submit" class="btnPurple hidden"
							id="loginButtonId2" style="float: right" value="Login" width="53"
							height="23" /> <a class="btnDark"
							style="float: right; margin-top: 3px;" id="edit" href="#"
							onClick="onclick=$('#loginButtonId2').click(); return true;">Login</a>
					</form>
				</div>
			</div>
 -->

		</div>
	</div>
	<div style="width: 600px; margin: 0 auto;">
		<br /> <br />Login with Username and Password<br />
	</div>

	<script type="text/javascript">
		$("#j_username").focus();
	</script>
</body>
</html>
