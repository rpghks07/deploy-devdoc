// 로그인 페이지 컴포넌트

import React from "react";
import { signin } from "../../service/ApiService";
import {
  Link,
  Button,
  TextField,
  Grid,
  Container,
  Typography,
} from "@mui/material";

class Login extends React.Component {
  constructor(props) {
    super(props);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  // 로그인 폼 제출 처리
  handleSubmit(event) {
    event.preventDefault();
    const data = new FormData(event.target);
    const email = data.get("email");
    const password = data.get("password");
    // ApiService의 signin 메서드를 사용 해 로그인.
    signin({ email: email, password: password });
  }

  render() {
    return (
        <Container component="main" maxWidth="xs" style={{ marginTop: "8%" }}>
          <Grid container spacing={2}>
            <Grid item xs={12}>
              <Typography component="h1" variant="h5">
                로그인
              </Typography>
            </Grid>
          </Grid>
          <form noValidate onSubmit={this.handleSubmit}>
            {" "}
            {/* submit 버튼을 누르면 handleSubmit이 실행됨. */}
            <Grid container spacing={2}>
              <Grid item xs={12}>
                <TextField
                    variant="outlined"
                    required
                    fullWidth
                    id="email"
                    label="이메일 주소"
                    name="email"
                    autoComplete="email"
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                    variant="outlined"
                    required
                    fullWidth
                    name="password"
                    label="패스워드"
                    type="password"
                    id="password"
                    autoComplete="current-password"
                />
              </Grid>
              <Grid item xs={12}>
                <Button
                    type="submit"
                    fullWidth
                    variant="contained"
                    color="primary"
                >
                  로그인
                </Button>
              </Grid>
              <Grid item xs={12}>
                <Button
                    href="/signup"
                    fullWidth
                    variant="outlined"
                    color="primary"
                    style={{ marginTop: '10px' }}
                >
                  회원 가입
                </Button>
              </Grid>
            </Grid>
          </form>
        </Container>
    );
  }
}

export default Login;
