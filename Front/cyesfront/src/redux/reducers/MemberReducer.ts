interface MemberState {
    member: {
        memberId: number;
        memberNickname: string;
        memberAuthority: string;
    };
}

const initialMemberState: MemberState = {
    member: {
        memberId: -1,
        memberNickname: "",
        memberAuthority: "",
    },
};

const memberReducer = (state = initialMemberState, action: any) => {
    switch (action.type) {
        case "MEMBER_SAVE":
            return { ...state, member: { ...action.payload } };

        default:
            return state;
    }
};

export default memberReducer;
