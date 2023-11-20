import SockJS from "sockjs-client";
import Stomp from "stompjs";
import {
    AnswerMessage,
    ChatMessage,
    ProblemMessage,
    ResultMessage,
    SessionMessage,
    SubmitMessage,
} from "./MessageInterface";

class QuizWebSocket {
    socket: WebSocket;
    client: Stomp.Client;
    quizId: number;
    receiveHandler: (message: any) => void = (message: any) => {};
    private reconnect: number = 0;

    constructor(quizId: number) {
        this.socket = new SockJS(
            `${process.env.REACT_APP_SPRING_SOCKET_URI}/ws/quiz`
        );
        this.client = Stomp.over(this.socket);
        this.client.heartbeat.incoming = 0;
        this.client.heartbeat.incoming = 0;
        this.quizId = quizId;
    }

    connect = () => {
        this.client.connect({}, this.subscribe, (err) => {
            console.log(err);
            if (this.reconnect < 5) {
                this.reconnect++;
                setTimeout(() => {
                    this.connect();
                }, 1000);
            }
        });
    };

    private subscribe = () => {
        this.client.subscribe("/sub/quiz/session/" + this.quizId, (message) => {
            const recvData = JSON.parse(message.body);

            this.receiveHandler(recvData);
        });

        this.sendEnter();
    };

    setMessageHandler = (handler: (message: any) => void) => {
        this.receiveHandler = handler;
    };

    sendSubmit = (data: SubmitMessage) => {
        this.client.send(
            "/pub/session/message/submit",
            {},
            JSON.stringify(data)
        );
    };

    sendChat = (data: ChatMessage) => {
        this.client.send("/pub/session/message/chat", {}, JSON.stringify(data));
    };

    sendEnter = () => {
        this.client.send(
            "/pub/session/message/enter",
            {},
            JSON.stringify({
                type: "ENTER",
                quizId: this.quizId,
            })
        );
    };

    sendLeave = () => {
        this.client.send(
            "/pub/session/message/disconnect",
            {},
            JSON.stringify({
                type: "ENTER",
                quizId: this.quizId,
            })
        );
    };

    disconnect = () => {
        if (this.client.connected) {
            this.client.disconnect(() => {
                console.log("channel disconnected");
            });
        }

        this.socket.close();
    };
}

export default QuizWebSocket;
