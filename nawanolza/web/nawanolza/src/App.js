import "./App.css";
import { useRef } from "react";
import styled from "styled-components";

// 이미지
import logoImg from "./assets/logo.png";
import googleImgDark from "./assets/btnGoogle.png";
import characterImg from "./assets/capture/character.png";
import mainhideImg from "./assets/capture/hidemain.png";
import backImg from "./assets/backgroundsub.png";
import apkImg from "./assets/apkdownload.png";

const Container = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
`;

const Box = styled.div`
  background-image: url(${backImg});
  background-size: cover;
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

const PhoneImg = styled.img`
  width: 50%;
`;

const Footer = styled.div`
  display: flex;
  margin-top: 50px;
  flex-direction: column;
  align-items: end;
  background-color: #373737;
  color: white;
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

const SBtn = styled.img`
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
  const downloadRef = useRef();
  const goGoogle = () => {
    window.alert("출시 준비 중인 서비스입니다.");
  };

  return (
    <>
      <Container className="App">
        <Box>
          <Banner>
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
            <SBtn src={googleImgDark} alt="google" onClick={goGoogle} />
            <SBtn
              src={apkImg}
              alt="apkImg"
              onClick={() => downloadRef.current.click()}
            />
            <a
              ref={downloadRef}
              href="/assets/app-release.apk"
              download
              style={{ display: "none" }}
            >
              Click Download
            </a>
          </Banner>
        </Box>
        <Content>
          <ContentItem>
            <PhoneImg src={mainhideImg} alt="phone" />
            <Title>
              다양한 게임으로
              <br />
              캐릭터를
              <br />
              수집해보세요
            </Title>
          </ContentItem>
          <ContentItem>
            <Title>
              실시간 위치를
              <br />
              활용한
              <br />
              숨바꼭질 게임
            </Title>
            <PhoneImg src={characterImg} alt="phone" />
          </ContentItem>
        </Content>
      </Container>
      <Footer>
        <p style={{ marginRight: "20px" }}>D103</p>
      </Footer>
    </>
  );
}

export default App;
