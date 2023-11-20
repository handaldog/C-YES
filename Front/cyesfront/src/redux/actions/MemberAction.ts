export const saveMember = (memberId: number, memberNickname: string, memberAuthority: string) => {
    return {
        type: "MEMBER_SAVE",
        payload: {
            memberId,
            memberNickname,
            memberAuthority,
        },
    };
};
