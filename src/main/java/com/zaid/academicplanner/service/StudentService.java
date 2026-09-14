package com.zaid.academicplanner.service;

import com.zaid.academicplanner.config.JwtService;
import com.zaid.academicplanner.dto.*;
import com.zaid.academicplanner.entity.AcademicEvent;
import com.zaid.academicplanner.entity.Course;
import com.zaid.academicplanner.entity.Lecture;
import com.zaid.academicplanner.entity.Student;
import com.zaid.academicplanner.exception.*;
import com.zaid.academicplanner.repository.StudentRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final CourseService courseService;
    private final AcademicEventService academicEventService;
    private final LectureService lectureService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public StudentService(StudentRepository studentRepository, CourseService courseService,
                          AcademicEventService academicEventService, LectureService lectureService,
                          PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.studentRepository = studentRepository;
        this.courseService = courseService;
        this.academicEventService = academicEventService;
        this.lectureService = lectureService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public StudentResponse addStudent(StudentCreateRequest studentRequest){
        if(studentRepository.existsByUniversityId(studentRequest.getUniversityId()))
            throw new DuplicateStudentException("Duplicate student");

        Student student = new Student(studentRequest.getUniversityId(),
                studentRequest.getName(), passwordEncoder.encode(studentRequest.getPassword()));

        Student savedStudent = studentRepository.save(student);
        return convertToResponse(savedStudent);
    }

    public Student getStudentEntityByUniversityId(String universityId){
        Optional<Student> student = studentRepository.findByUniversityId(universityId);
        if(student.isEmpty())
            throw new StudentNotFoundException("Student not found");
        return student.get();
    }

    public StudentResponse getStudentByUniversityId(String universityId){
        Student student = getStudentEntityByUniversityId(universityId);
        return convertToResponse(student);
    }

    public StudentResponse removeStudent(String universityId){
        Student student = getStudentEntityByUniversityId(universityId);

        List<Course> courses = new ArrayList<>(student.getCourses());
        List<AcademicEvent> events = new ArrayList<>(student.getEvents());

        StudentResponse response = new StudentResponse(student.getStudentId(), student.getUniversityId(),
                student.getName(), courses, events);

        studentRepository.delete(student);
        return response;
    }

    public List<StudentResponse> getAllStudents(){
        List<Student> students = studentRepository.findAll();
        List<StudentResponse> studentResponses = new ArrayList<>();

        for(Student student : students){
            studentResponses.add(convertToResponse(student));
        }

        return studentResponses;
    }

    public StudentResponse editStudentInfo(String universityId, StudentEditRequest editRequest){
        Student student = getStudentEntityByUniversityId(universityId);
        student.setName(editRequest.getName());
        student.setPassword(passwordEncoder.encode(editRequest.getPassword()));
        Student savedStudent = studentRepository.save(student);
        return convertToResponse(savedStudent);
    }

    public List<Course> enrollStudentInCourse(String universityId, String courseCode){
        Student student = getStudentEntityByUniversityId(universityId);
        Course course = courseService.getCourseByCode(courseCode);
        boolean courseExist = false;
        for(Course searchedCourse : student.getCourses()){
            if(searchedCourse.getCourseCode().equals(courseCode)){
                courseExist = true;
                break;
            }
        }
        if(courseExist)
            throw new DuplicateEnrollmentException("Duplicate enrollment");

        student.getCourses().add(course);
        studentRepository.save(student);
        return student.getCourses();
    }

    public List<Course> removeStudentFromCourse(String universityId, String courseCode){
        Student student = getStudentEntityByUniversityId(universityId);
        Course course = courseService.getCourseByCode(courseCode);
        boolean courseExist = false;
        for(Course searchedCourse : student.getCourses()) {
            if (searchedCourse.getCourseCode().equals(courseCode)) {
                courseExist = true;
                course = searchedCourse;
                break;
            }
        }

        if(!courseExist)
            throw new StudentNotEnrolledException("Student not enrolled");
        student.getCourses().remove(course);
        studentRepository.save(student);
        return student.getCourses();
    }

    public List<AcademicEvent> enrollStudentInEvent(String universityId, Integer eventId){
        Student student = getStudentEntityByUniversityId(universityId);
        AcademicEvent event = academicEventService.getEventById(eventId);
        boolean eventExist = false;
        for(AcademicEvent searchedEvent : student.getEvents()){
            if(searchedEvent.getEventId().equals(eventId)){
                eventExist = true;
                break;
            }
        }
        if(eventExist)
            throw new DuplicateEnrollmentException("Duplicate enrollment");

        student.getEvents().add(event);
        studentRepository.save(student);
        return student.getEvents();
    }

    public List<AcademicEvent> removeStudentFromEvent(String universityId, Integer eventId){
        Student student = getStudentEntityByUniversityId(universityId);
        AcademicEvent event = academicEventService.getEventById(eventId);
        boolean eventExist = false;
        for(AcademicEvent searchedEvent : student.getEvents()){
            if(searchedEvent.getEventId().equals(eventId)){
                eventExist = true;
                event = searchedEvent;
                break;
            }
        }
        if(!eventExist)
            throw new StudentNotEnrolledException("Student not enrolled");

        student.getEvents().remove(event);
        studentRepository.save(student);
        return student.getEvents();
    }

    public List<Lecture> getLecturesSchedule(String universityId){
        Student student = getStudentEntityByUniversityId(universityId);
        List<Course> studentCourses = student.getCourses();
        List<Lecture> studentLectures = new ArrayList<>();
        for(Course searchedCourse : studentCourses){
            studentLectures.addAll(lectureService.getLecturesByCourseCode(searchedCourse.getCourseCode()));
        }
        studentLectures.sort(Comparator.comparing((Lecture lecture) -> lecture.getDayOfWeek().getValue())
                .thenComparing(Lecture::getStartTime)
        );
        return studentLectures;
    }

    public List<Course> getStudentCourses(String universityId){
        Student student = getStudentEntityByUniversityId(universityId);
        return student.getCourses();
    }

    public List<AcademicEvent> getStudentEvents(String universityId){
        Student student = getStudentEntityByUniversityId(universityId);
        List<AcademicEvent> events = student.getEvents();
        List<AcademicEvent> eventsCopy = new ArrayList<>(events);
        eventsCopy.sort(Comparator.comparing(AcademicEvent::getDate).thenComparing(AcademicEvent::getStartTime));
        return eventsCopy;
    }

    public StudentResponse convertToResponse(Student student){
        return new StudentResponse(student.getStudentId(), student.getUniversityId(),
                student.getName(), student.getCourses(), student.getEvents());
    }

    public LoginResponse login(LoginRequest loginRequest){
        Student student = getStudentEntityByUniversityId(loginRequest.getUniversityId());
        if(!passwordEncoder.matches(loginRequest.getPassword(), student.getPassword()))
        throw new AuthenticationRelatedException("Authentication related exception");
        String token = jwtService.generateToken(loginRequest.getUniversityId());
        return new LoginResponse(token, convertToResponse(student));
    }

}