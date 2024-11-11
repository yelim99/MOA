export type MomentInfo = {
  momentId: string;
  momentName: string;
  momentOwner: string;
  createdAt: string;
};

export type MomentInfoDetail = {
  id: string;
  groupId: string;
  momentPin: string;
  userNicknames: string[];
  momentName: string;
  momentDescription: string;
  momentOwner: string;
  createdAt: string;
  uploadOption: string;
};

export type MomentAddInfo = {
  momentId: string;
  momentName: string;
  momentDescription: string;
  uploadOption: string;
};
