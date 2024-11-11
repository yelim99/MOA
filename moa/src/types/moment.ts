export type MomentInfo = {
  momentId: string;
  momentName: string;
  momentOwner: string;
  createdAt: string;
};

export type MomentInfoDetail = {
  momentId: string;
  momentName: string;
  momentOwner: string;
  momentDescription: string;
  createdAt: string;
};

export type MomentPostResponse = {
  momentId: string;
  message: string;
  pin: string;
};
