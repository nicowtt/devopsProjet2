import { TestBed } from '@angular/core/testing';

import {provideHttpClient} from '@angular/common/http';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';

import { StudentService } from './student.service';
import { AuthService } from './auth.service';
import { StudentListDTO, StudentSaveDTO } from '../models/student.model';

describe('StudentService', () => {
  let service: StudentService;
  let httpMock: HttpTestingController;
  let authServiceMock: jest.Mocked<AuthService>;

  beforeEach(() => {
    authServiceMock = {
      getToken: jest.fn().mockReturnValue('jwtToken')
    } as any;

    TestBed.configureTestingModule({
      providers: [
        provideHttpClient(),
        provideHttpClientTesting(),
        { provide: AuthService, useValue: authServiceMock },
      ]
    });
    service = TestBed.inject(StudentService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  // instanciate service
  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  // create student unit test
  describe('createStudent()', () => {
    it('must send POST request to /api/createStudent with correct data', () => {
      // GIVEN
      const studentSaveDTO: StudentSaveDTO  = { firstName: 'testFirst', lastName: 'testLast', email: 'email@email.com'}

      // WHEN
      service.createStudent(studentSaveDTO).subscribe();

      // THEN
      const req = httpMock.expectOne('/api/createStudent');
      expect(req.request.method).toBe('POST');
      expect(req.request.headers.get('Authorization')).toBe('Bearer jwtToken');
      expect(req.request.body).toEqual(studentSaveDTO);
      req.flush(null, { status: 201, statusText: 'Created' });
    })
  })

  // update student
  describe('updateStudent()', () => {
    it('must send PUT request to /api/updateStudent/${uuid} with correct data', () => {
      // GIVEN
      const studentSaveDTO: StudentSaveDTO = { firstName: 'testFirst', lastName: 'testLast', email: 'email@email.com' }
      const uuid = 'EA4656AA-2647-432B-AA53-C4E8E178945D';

      // WHEN
      service.updateStudent(uuid, studentSaveDTO).subscribe();

      // THEN
      const req = httpMock.expectOne(`/api/updateStudent/${uuid}`);
      expect(req.request.method).toBe('PUT');
      expect(req.request.headers.get('Authorization')).toBe('Bearer jwtToken');
      expect(req.request.body).toEqual(studentSaveDTO);
      req.flush(null, { status: 200, statusText: 'OK' });
    })
  })

  // getByUUID
  describe('getStudentByUuid()', () => {
    it('must send GET request to /api/getStudent/${uuid} with correct data', () => {
      // GIVEN
      const uuid = 'EA4656AA-2647-432B-AA53-C4E8E178945D';
      const studentDTO = {
        email: 'test@test.com', updatedAt: '2026-05-12 11:24:34.529833', createdAt: '2026-05-12 11:24:34.529833'
      };

      // WHEN
      service.getStudentByUuid(uuid).subscribe();

      // THEN
      const req = httpMock.expectOne(`/api/getStudent/${uuid}`);
      expect(req.request.method).toBe('GET');
      expect(req.request.headers.get('Authorization')).toBe('Bearer jwtToken');
      req.flush(studentDTO);
    })
  })

  // getStudents
  describe('getStudents()', () => {
    it('must send GET request to /api/getStudentList with correct data', () => {
      // GIVEN
      const uuid1 = 'EA4656AA-2647-432B-AA53-C4E8E178945D';
      const firstName1 = 'firstName1';
      const lastName1 = 'lastName1';

      const uuid2 = '9B170DEB-41BB-44DA-AFAA-8FD4BC487706';
      const firstName2 = 'firstName2';
      const lastName2 = 'lastName2';

      const students: StudentListDTO[] = [
        { uuid: uuid1, firstName: firstName1, lastName: lastName1 },
        { uuid: uuid2, firstName: firstName2, lastName: lastName2 }
      ];

      // WHEN
      service.getStudents().subscribe();

      // THEN
      const req = httpMock.expectOne(`/api/getStudentList`);
      expect(req.request.method).toBe('GET');
      expect(req.request.headers.get('Authorization')).toBe('Bearer jwtToken');
      req.flush(students);
    })
  })

  // delete
  describe('deleteStudent()', () => {
    it('must send DELETE request to /api/deleteStudent/${uuid} with correct data', () => {
      // given
      const uuid = 'EA4656AA-2647-432B-AA53-C4E8E178945D';

      // WHEN
      service.deleteStudent(uuid).subscribe();

      // THEN
      const req = httpMock.expectOne(`/api/deleteStudent/${uuid}`);
      expect(req.request.method).toBe('DELETE');
      expect(req.request.headers.get('Authorization')).toBe('Bearer jwtToken');
      req.flush(null, { status: 204, statusText: 'No Content' });
    })
  })

});
