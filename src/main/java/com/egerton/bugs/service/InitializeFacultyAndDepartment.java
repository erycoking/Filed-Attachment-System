package com.egerton.bugs.service;

import com.egerton.bugs.Model.Department;
import com.egerton.bugs.Model.Faculty;
import com.egerton.bugs.repositories.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class InitializeFacultyAndDepartment {

    private FacultyRepository facultyRepo;

    @Autowired
    public InitializeFacultyAndDepartment(FacultyRepository facultyRepo) {
        this.facultyRepo = facultyRepo;
    }

    public void initFacultyAndDepartment(){

        Faculty faculty = new Faculty();
        faculty.setFacultyName("FACULTY OF AGRICULTURE");
        faculty.setDepartment(Arrays.asList(
                new Department("Department of Animal Sciences", faculty),
                new Department("Department of Dairy, Food Science and Technology", faculty),
                new Department("Department of Crops, Horticulture and Soil Sciences", faculty),
                new Department("Department of Agriculture Economics and Agribusiness Management", faculty)
        ));
        facultyRepo.save(faculty);

        Faculty faculty1 = new Faculty();
        faculty1.setFacultyName("FACULTY OF ARTS AND SOCIAL SCIENCES");
        faculty1.setDepartment(Arrays.asList(
                new Department("Department of Economics", faculty1),
                new Department("Department of Languages, Literature and Linguistics", faculty1),
                new Department("Department of Peace Security and Social Studies", faculty1),
                new Department("Department of Philosophy, History and Religion", faculty1)
        ));
        facultyRepo.save(faculty1);

        Faculty faculty2 = new Faculty();
        faculty2.setFacultyName("FACULTY OF COMMERCE");
        faculty2.setDepartment(Arrays.asList(
                new Department("Department of Business Administration", faculty2),
                new Department("Department of Accounting , Finance and Management", faculty2)
        ));
        facultyRepo.save(faculty2);

        Faculty faculty3 = new Faculty();
        faculty3.setFacultyName("FACULTY OF EDUCATION AND COMMUNITY DEVELOPMENT STUDIES");
        faculty3.setDepartment(Arrays.asList(
                new Department("Department of Agricultural Education and Extension", faculty3),
                new Department("Department of Applied Community Development Studies", faculty3),
                new Department("Department of Curriculum, Instruction and Educational Management", faculty3),
                new Department("Department of Psychology, Counseling and Educational Foundations", faculty3)
        ));
        facultyRepo.save(faculty3);

        Faculty faculty4 = new Faculty();
        faculty4.setFacultyName("FACULTY OF ENGINEERING AND TECHNOLOGY");
        faculty4.setDepartment(Arrays.asList(
                new Department("Department of Agricultural Engineering", faculty4),
                new Department("Department of Civil and Environmental Engineering", faculty4),
                new Department("Department of Industrial and Energy Engineering", faculty4),
                new Department("Department of Electrical and Control Engineering", faculty4)
        ));
        facultyRepo.save(faculty4);

        Faculty faculty5 = new Faculty();
        faculty5.setFacultyName("FACULTY OF ENVIRONMENT AND RESOURCES DEVELOPMENT");
        faculty5.setDepartment(Arrays.asList(
                new Department("Department of Environmental Science", faculty5),
                new Department("Department of Geography", faculty5),
                new Department("Department of Natural Resources", faculty5)
        ));
        facultyRepo.save(faculty5);

        Faculty faculty6 = new Faculty();
        faculty6.setFacultyName("FACULTY OF HEALTH SCIENCES");
        faculty6.setDepartment(Arrays.asList(
                new Department("Department of Anatomy", faculty6),
                new Department("Department of Internal Medicine", faculty6),
                new Department("Department of Child Health and Paediatrics", faculty6),
                new Department("Department of Clinical Medicine and Surgery", faculty6),
                new Department("Department of Community Health", faculty6),
                new Department("Department of Human Nutrition", faculty6),
                new Department("Department of Reproductive Health", faculty6),
                new Department("Department of Surgery", faculty6),
                new Department("Department of Pathology", faculty6),
                new Department("Department of Nursing", faculty6),
                new Department("Department of Medical Physiology", faculty6)
        ));
        facultyRepo.save(faculty6);

        Faculty faculty7 = new Faculty();
        faculty7.setFacultyName("FACULTY OF LAW");
        faculty7.setDepartment(Arrays.asList(
                new Department("Department of LAW", faculty7)
        ));
        facultyRepo.save(faculty7);

        Faculty faculty8 = new Faculty();
        faculty8.setFacultyName("FACULTY OF SCIENCE");
        faculty8.setDepartment(Arrays.asList(
                new Department("Department of Chemistry", faculty8),
                new Department("Department of Biochemistry and Molecular Biology", faculty8),
                new Department("Department of Computer Science", faculty8),
                new Department("Department of Physics", faculty8),
                new Department("Department of Mathematics", faculty8),
                new Department("Department of Biological Sciences", faculty8)
        ));
        facultyRepo.save(faculty8);

        Faculty faculty9 = new Faculty();
        faculty9.setFacultyName("FACULTY OF VETERINARY MEDICINE AND SURGERY");
        faculty9.setDepartment(Arrays.asList(
                new Department("Department of Veterinary Anatomy and Physiology", faculty9),
                new Department("Department of Veterinary Parasitology, Microbiology and Pathology", faculty9),
                new Department("Department of Veterinary Veterinary Pharmacology Public Health and Toxicology", faculty9),
                new Department("Department of Veterinary Surgery Medicine and Theriogenology", faculty9)
        ));
        facultyRepo.save(faculty9);

        Faculty faculty10 = new Faculty();
        faculty10.setFacultyName("INSTITUTE OF WOMEN GENDER AND DEVELOPMENT STUDIES");
        faculty10.setDepartment(Arrays.asList(
                new Department("Department of WOMEN GENDER AND DEVELOPMENT STUDIES", faculty10)
        ));
        facultyRepo.save(faculty10);

        Faculty faculty11 = new Faculty();
        faculty11.setFacultyName("COLLEGE OF OPEN AND DISTANCE LEARNING");
        faculty11.setDepartment(Arrays.asList(
                new Department("Department of Military Science", faculty11),
                new Department("Department of Teacher Education", faculty11),
                new Department("Department of Instructional Material Development", faculty11),
                new Department("Department of External Studies and Media", faculty11)
        ));
        facultyRepo.save(faculty11);

    }
}
