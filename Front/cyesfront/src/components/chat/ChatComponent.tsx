import React, { useEffect, useRef, useState } from "react";
import "./ChatComponent.css";
import { ChatMessage } from "../../api/websocket/MessageInterface";

interface Props {
    quizId: number;
    memberId: number;
    memberNickname: string;
    chatList: ChatMessage[];
    socketSend: (chat: ChatMessage) => void;
}

const ChatComponent: React.FC<Props> = ({
    quizId,
    memberId,
    memberNickname,
    chatList,
    socketSend,
}) => {
    const [chat, setChat] = useState<string>("");
    const [chatDisabled, setChatDisabled] = useState<boolean>(false);

    const chatCounter = useRef<number>(0);
    const chatCounterInit = useRef<NodeJS.Timeout>();
    const chatScroll = useRef<HTMLDivElement>(null);

    useEffect(() => {
        if (chatScroll.current) chatScroll.current.scrollTop = chatScroll.current.scrollHeight;
    }, [chatList]);

    const onChatTyped = (e: React.ChangeEvent<HTMLInputElement>) => {
        setChat(e.target.value);
    };

    const sendChat = () => {
        if (chat === "" || chatDisabled) {
            return;
        }

        if (chatCounter.current > 4) {
            setChatDisabled(true);

            setTimeout(() => {
                setChatDisabled(false);
            }, 5000);
            return;
        }

        socketSend({
            quizId,
            type: "CHAT",
            memberId,
            message: memberNickname + ":" + chat,
        });
        chatCounter.current++;
        setChat("");

        if (chatCounterInit.current) {
            clearTimeout(chatCounterInit.current);
        }

        chatCounterInit.current = setTimeout(() => {
            chatCounter.current = 0;
        }, 3000);
    };

    const handleKeyPress = (e: React.KeyboardEvent) => {
        if (e.key === "Enter") {
            sendChat();
        }
    };

    return (
        <div className="chatbox">
            <div className="chat-list-box" ref={chatScroll}>
                {chatList.map((message, idx) => {
                    return message.memberId === memberId ? (
                        <div key={idx} className="my-chat">
                            {message.message.slice(message.message.indexOf(":") + 1)}
                        </div>
                    ) : (
                        <div key={idx} className="other-chat">
                            {message.message}
                        </div>
                    );
                })}
            </div>
            <div className="chat-input">
                <input
                    value={chat}
                    onChange={onChatTyped}
                    onKeyDown={handleKeyPress}
                    placeholder={chatDisabled ? "잠시 기다려주세요" : "채팅을 입력해 주세요"}
                />
                <button onClick={sendChat} disabled={chatDisabled}>
                    입력
                </button>
            </div>
        </div>
    );
};

export default ChatComponent;
