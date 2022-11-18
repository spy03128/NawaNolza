import "./App.css";
import { useState } from "react";
import styled from "styled-components";

// 이미지
import logoImg from "./assets/logo.png";
import googleImgDark from "./assets/btnGoogle.png";
import googleImg from "./assets/btnGoogleLight.png";
import phoneImg from "./assets/phone.png";
import backImg from "./assets/backgroundsub.png";

const Container = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
`;

const Box = styled.div`
  background-image: url(${backImg});
  background-size: cover;
`;

const Navbar = styled.div`
  background-color: ${(props) => props.back};
`;

const Banner = styled.div`
  padding: 0 20vw;
  display: flex;
  flex-direction: column;
  background-size: cover;
  height: 1000px;
`;

const Content = styled.div`
  display: flex;
  padding: 0 20vw;
  margin-top: 100px;
`;

const Footer = styled.div`
  display: flex;
  padding: 0 20vw;
  flex-direction: column;
  background-color: #373737;
  color: white;
`;

const FooterContent = styled.div`
  display: flex;
  justify-content: center;
  flex-direction: column;
`;

const ContentItem = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  flex-grow: 1;
`;

const LogoImg = styled.img`
  width: 150px;
  height: 150px;
`;

const PhoneImg = styled.img`
  width: 100%;
`;

const GoogleBtn = styled.img`
  width: 200px;
  height: 80px;
  cursor: pointer;
  margin-left: 20px;
`;

const TitleContainer = styled.div`
  display: flex;
  flex-direction: column;
  text-align: start;
  margin-left: 30px;
  color: white;
`;

const Title = styled.p`
  font-size: 48px;
  font-weight: bold;
  line-height: 1.8;
`;

function App() {
  const [active, setActive] = useState(false);

  const clickGoogle = () => {
    window.alert("준비 중인 서비스입니다.");
  };
  return (
    <>
      <Container className="App">
        <Box>
          <Banner>
            {/* <Navbar back={active ? "black" : "white"}>나와, 놀자</Navbar> */}
            <LogoImg src={logoImg} alt="logo" />
            <TitleContainer>
              <Title>
                친구들과
                <br />
                뭐하지?
                <br />
                나와, 놀자!
              </Title>
            </TitleContainer>
            <GoogleBtn src={googleImgDark} alt="google" onClick={clickGoogle} />
          </Banner>
        </Box>
        <Content>
          <ContentItem>
            <PhoneImg src={phoneImg} alt="phone" />
            <Title>
              지도 기반의
              <br />
              숨바꼭질부터
              <br />
              멘트 뭐하죠
            </Title>
          </ContentItem>
          <ContentItem>
            <Title>
              지도 기반의
              <br />
              숨바꼭질부터
              <br />
              멘트 뭐할까
            </Title>
            <PhoneImg src={phoneImg} alt="phone" />
          </ContentItem>
        </Content>
      </Container>
      <Footer>
        <h1>나와, 놀자!</h1>
        <FooterContent>
          <p>Footer 할꺼야?</p>
        </FooterContent>
      </Footer>
    </>
  );
}

export default App;
