package usecase;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import api.GradeDataBase;
import entity.Grade;
import entity.Team;

/**
 * GetAverageGradeUseCase class.
 */
public final class GetAverageGradeUseCase {
    private final GradeDataBase gradeDataBase;

    public GetAverageGradeUseCase(GradeDataBase gradeDataBase) {
        this.gradeDataBase = gradeDataBase;
    }

    /**
     * Get the average grade for a course across your team.
     * @param course The course.
     * @return The average grade.
     */
    public float getAverageGrade(String course) {
        // Call the API to get usernames of all your team members
        float sum = 0;
        AtomicInteger count = new AtomicInteger();
        // TODO Task 3b: Go to the MongoGradeDataBase class and implement getMyTeam.
        final Team team = gradeDataBase.getMyTeam();
        // Call the API to get all the grades for the course for all your team members
        // TODO Task 3a: Complete the logic of calculating the average course grade for
        //              your team members. Hint: the getGrades method might be useful.
        for (String member : team.getMembers()) {
            sum += (float) Arrays.stream(gradeDataBase.getGrades(member))
                    .filter(grade -> grade.getCourse().equals(course))
                    .peek(grade -> count.getAndIncrement())
                    .mapToDouble(Grade::getGrade)
                    .sum();
        }

        if (count.get() == 0) {
            return 0;
        }
        return sum / count.get();
    }
}
