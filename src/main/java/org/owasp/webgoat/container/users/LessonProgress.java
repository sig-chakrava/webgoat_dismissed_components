/*
 * SPDX-FileCopyrightText: Copyright © 2008 WebGoat authors
 * SPDX-License-Identifier: GPL-2.0-or-later
 */
package org.owasp.webgoat.container.users;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Version;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.owasp.webgoat.container.lessons.Lesson;

@Entity
public class LessonProgress {

@Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
@Getter private String lessonName;
  
@Override
  public boolean equals(Object o) {
  if (this == o) return true;
if (o == null || getClass() != o.getClass()) return false;
  LessonProgress that = (LessonProgress) o;
  return id != null && id.equals(that.id);
}
  
    @Override
  public int hashCode() {
return id != null ? id.hashCode() : 0;
  }
    lessonName = lesson.getId();
    assignments.addAll(lesson.getAssignments().stream().map(AssignmentProgress::new).toList());
  }

  private Optional<AssignmentProgress> getAssignment(String name) {
    return assignments.stream().filter(a -> a.hasSameName(name)).findFirst();
  }

  /**
   * Mark an assignment as solved
   *
   * @param solvedAssignment the assignment which the user solved
   */
  public void assignmentSolved(String solvedAssignment) {
    getAssignment(solvedAssignment).ifPresent(AssignmentProgress::solved);
  }

  /**
   * @return did they user solved all solvedAssignments for the lesson?
   */
  public boolean isLessonSolved() {
    return assignments.stream().allMatch(AssignmentProgress::isSolved);
  }

  /** Increase the number attempts to solve the lesson */
  public void incrementAttempts() {
    numberOfAttempts++;
  }

  /** Reset the tracker. We do not reset the number of attempts here! */
  void reset() {
    assignments.forEach(AssignmentProgress::reset);
  }

  /**
   * @return list containing all the assignments solved or not
   */
  public Map<AssignmentProgress, Boolean> getLessonOverview() {
    return assignments.stream().collect(Collectors.toMap(a -> a, AssignmentProgress::isSolved));
  }

  long numberOfSolvedAssignments() {
    return assignments.stream().filter(AssignmentProgress::isSolved).count();
  }
}
