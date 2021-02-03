package model;

public class CourseBag 
{
	private Course[] courseArrayTook;
	private Course[] courseArrayCurrent;
	private Course[] courseArrayWillTake;
	private int nElemsTook = 0;
	private int nElemsCurrent = 0;
	private int nElemsWillTake = 0;
	
	public CourseBag(int maxSize)
	{
		courseArrayTook = new Course[maxSize];
	}
	
	public void addTook(Course course) 
	{
		courseArrayTook[nElemsTook] = course;
		nElemsTook++;
	}
	
	public void addCurrent(Course course)
	{
		courseArrayCurrent[nElemsCurrent] = course;
		nElemsCurrent++;
	}
	
	public void addWillTake(Course course) 
	{
		courseArrayWillTake[nElemsWillTake] = course;
		nElemsWillTake++;
	}
	
	public void display()
	{
		System.out.println("\nCourses taken: ");
		for (int i = 0; i < nElemsTook; i++) 
		{
			System.out.println(courseArrayTook[i]);
		}
		System.out.println("\nCourses currently taking: ");
		for (int i = 0; i < nElemsCurrent; i++) 
		{
			System.out.println(courseArrayCurrent[i]);
		}
		System.out.println("\nCourses that has to be took: ");
		for (int i = 0; i < nElemsWillTake; i++) 
		{
			System.out.println(courseArrayWillTake[i]);
		}
	}
	
	public String findTook(String courseNumber) 
	{
		for(int i = 0; i < nElemsTook; i++) 
		{
			if(courseArrayTook[i].getCourseNumber().equals(courseNumber)) 
			{
				return courseArrayTook[i].toString();
			}
		}
		return null;
	}
	
	public String findCurrent(String courseNumber)
	{
		for(int i = 0; i < nElemsCurrent; i++) 
		{
			if(courseArrayCurrent[i].equals(courseNumber))
			{
				return courseArrayCurrent[i].getCourseNumber().toString();
			}
		}
		return null;
	}
	
	public String findWillTake(String courseNumber) 
	{
		for(int i = 0; i < nElemsWillTake; i++) 
		{
			if(courseArrayWillTake[i].equals(courseNumber))
			{
				return courseArrayWillTake[i].getCourseNumber().toString();
			}
		}
		return null;
	}
	
	public String deleteTook(String courseNumber)
	{
		int i;
		for (i = 0; i < nElemsTook; i++) 
		{
			if (courseArrayTook[i].getCourseNumber().equals(courseNumber))
			{
				break;
			}
		}
		if (i == nElemsTook) 
		{
			return null;
		}
		else 
		{
			Course temp = courseArrayTook[i];
			for(int j = i; j < nElemsTook - 1; j++) 
			{
				courseArrayTook[j]= courseArrayTook[j+1];
			}
			nElemsTook--;
			return temp.toString();
		}
	}
	
	public String deleteCurrent(String courseNumber)
	{
		int i;
		for (i = 0; i < nElemsCurrent; i++) 
		{
			if (courseArrayCurrent[i].getCourseNumber().equals(courseNumber)) 
			{
				break;
			}
		}
		if (i == nElemsCurrent)
		{
			return null;
		}
		else {
			Course temp = courseArrayCurrent[i];
			for(int j = i; j < nElemsCurrent - 1; j++) 
			{
				courseArrayCurrent[j] = courseArrayCurrent[j+1];
			}
			nElemsCurrent--;
			return temp.toString();
		}
	}
	
	public String deleteWillTake(String courseNumber) {
		int i;
		for (i = 0; i < nElemsWillTake; i++) {
			if (courseArrayWillTake[i].getCourseNumber().equals(courseNumber)) {
				break;
			}
		}
		if (i == nElemsWillTake) {
			return null;
		}
		else {
			Course temp = courseArrayWillTake[i];
			for(int j = i; j < nElemsWillTake - 1; j++) {
				courseArrayWillTake[j]= courseArrayWillTake[j+1];
			}
			nElemsWillTake--;
			return temp.toString();
		}
	}

}
