export interface StudentDTO extends StudentListDTO{
  email: string;
  createdAt: string;
  updatedAt: string;
}

export interface StudentListDTO {
  uuid: string;
  firstName: string;
  lastName: string;
}

export interface StudentSaveDTO {
  firstName: string;
  lastName: string;
  email: string;
}
