package nlr.ganymede.simulation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import nlr.utils.Utils;

import org.newdawn.slick.geom.Vector2f;

public strictfp final class GanymedePath {
	
	private ArrayList<Vector2f> waypoints;

	public List<Vector2f> getWaypoints() {
		return new ArrayList<Vector2f>(waypoints);
	}

	public GanymedePath(Collection<Vector2f> waypoints) {
		
		super();
		
		this.waypoints = new ArrayList<Vector2f>(waypoints);
	}
	
	public Vector2f getStart() {
		return this.waypoints.get(0);
	}
	
	public Vector2f getEnd() {
		return this.waypoints.get(this.waypoints.size() - 1);
	}
	
	public Vector2f getWaypoint(int index) {
		return this.waypoints.get(index);
	}
	
	public int getLength() {
		return this.waypoints.size();
	}
	
	public float getDistanceAlongPath(Vector2f position) {
		
		boolean foundCandidate = false;
		
		float bestCandidateScore = 0;
		float bestCandidate = 0;
		
		float distance = 0;
		
		for (int i = 0; i < this.waypoints.size() - 1; i++) {
			
			Vector2f a = this.getWaypoint(i);
			Vector2f b = this.getWaypoint(i + 1);
			
			Vector2f candidatePosition = Utils.closestPointOnLineSegment(position, a, b);
			
			float candidateScore = position.distanceSquared(candidatePosition);
			
			if ((!foundCandidate) || (candidateScore <= bestCandidateScore)) {
				
				bestCandidateScore = candidateScore;
				
				bestCandidate = distance + a.distance(candidatePosition);
				
				if (!foundCandidate) {
					foundCandidate = true;
				}
			}
			
			distance += a.distance(b);
		}
		
		return bestCandidate;
	}
	
	public Vector2f getPointAlongPath(float distance) {
		
		if (distance <= 0) {
			return this.waypoints.get(0);	
		}
		
		float distanceSearched = 0;
		
		for (int i = 0; i < this.waypoints.size() - 1; i++) {
			
			Vector2f a = this.getWaypoint(i);
			Vector2f b = this.getWaypoint(i + 1);
			
			Vector2f segment = b.copy().sub(a);
			
			float segmentLength = segment.length();
			
			if (distanceSearched + segmentLength > distance) {
				return a.copy().add(segment.copy().normalise().scale(distance - distanceSearched));
			}
			
			distanceSearched += segmentLength;
		}
		
		return this.waypoints.get(this.waypoints.size() - 1);
	}
}
