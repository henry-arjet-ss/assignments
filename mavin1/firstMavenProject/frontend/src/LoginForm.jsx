import React, {useState } from 'react';
import { Form, Button } from 'react-bootstrap';
import { useFormik, } from 'formik';

function LoginForm(){
    const [head, setHead] = useState({style:{}, text:"Please Log In"});
    
    const formik = useFormik({
        initialValues: {
          username: '',
          password: '',
        },
        validate,
        onSubmit: values => {handleSubmit(values);},
    });
    function validate(values){
        const errors = {};
        if(!values.username){
            errors.username = "Required";
        }
        if(!values.password){
            errors.password = "Required";
        }
        return errors;
    }
    function handleSubmit(values){
        fetch(window.location.origin + "/firstMavenProject/login", {
            method: "POST",
            headers: {'Content-Type': 'application/json',},
            body: JSON.stringify({username: values.username, password: values.password})
        }).then(response => {
            if (response.status === 200) {
                setHead({styles: {color:"green"},text: "You're in"});
                
            } else if (response.status === 401) {
                formik.values.password = "";
                setHead({styles: {color:"red"},text: "Invalid username or password"});
            } else{
                console.log("something went wrong :{");
                setHead({styles: {color:"red"},text: "Something went wrong. Sorry about that."});
            }
        });
    }
    return (
        <Form noValidate onSubmit={formik.handleSubmit}>
            <h2 style={head.style}>{head.text}</h2>
            <Form.Group controlId="username">
                <Form.Label>Username</Form.Label>
                <Form.Control required type="text"
                value={formik.values.username}
                onChange={formik.handleChange}
                onBlur={formik.handleBlur}
                isInvalid={formik.touched.username && formik.errors.username }/>
                <Form.Control.Feedback type="invalid" >
                    Please enter your username
                </Form.Control.Feedback>
            </Form.Group>
            <Form.Group controlId="password">
                <Form.Label>Password</Form.Label>
                <Form.Control required type="password"
                value={formik.values.password}
                onBlur={formik.handleBlur}
                onChange={formik.handleChange}
                isInvalid={formik.touched.password && formik.errors.password } />
                <Form.Control.Feedback type="invalid">
                    Please enter your password
                </Form.Control.Feedback>
            </Form.Group>

            <br/>
            <Button variant="dark" type="submit">Let's Do It</Button>
        </Form>
    )
  }
  export default LoginForm;