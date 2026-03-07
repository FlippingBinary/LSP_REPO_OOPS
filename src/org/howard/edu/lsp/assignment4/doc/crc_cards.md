# ATC System — CRC Card Design

## **CRC Card**

Class: Transponder

Responsibilities:

- Broadcast aircraft type and flight data in high-density packet format.
- Encode current position, altitude, speed, and heading into a data packet.

Collaborators (if any):

- GroundStation

Assumptions (if any):

- Each aircraft has exactly one transponder.
- The transponder broadcasts continuously at a fixed interval.

---

## **CRC Card**

Class: GroundStation

Responsibilities:

- Receive high-density data packets from aircraft transponders.
- Unpack and decode received data packets into structured flight information.
- Forward decoded aircraft data to the aircraft database for storage.

Collaborators (if any):

- Transponder
- Aircraft
- AirTraffic

Assumptions (if any):

- The ground station handles all incoming transponder signals for the single airport.

---

## **CRC Card**

Class: Aircraft

Responsibilities:

- Store flight attributes for a single aircraft (type, flight number, position, altitude, speed, heading).
- Provide access to current flight data when queried.
- Update flight attributes when new data is received.

Collaborators (if any):

- None

Assumptions (if any):

- Acts as a data-bearing domain object representing one aircraft's current state.

---

## **CRC Card**

Class: AirTraffic

Responsibilities:

- Store and manage a collection of Aircraft records.
- Add new Aircraft entries when previously unseen aircraft are detected.
- Update existing Aircraft records with incoming flight data.
- Remove Aircraft records when an aircraft is no longer in range.
- Provide aircraft data to other components on request.

Collaborators (if any):

- Aircraft

Assumptions (if any):

- Serves as the single authoritative source of current aircraft information.

---

## **CRC Card**

Class: DisplayRenderer

Responsibilities:

- Build a graphical representation of all tracked aircraft from stored data.
- Refresh the display every 10 seconds with current aircraft positions.
- Highlight aircraft involved in dangerous situations on the display.

Collaborators (if any):

- AirTraffic
- ProximityAnalyzer

Assumptions (if any):

- The display shows a radar-style view of the airspace around the airport.

---

## **CRC Card**

Class: ProximityAnalyzer

Responsibilities:

- Analyze current aircraft positions and trajectories to detect dangerous situations.
- Evaluate separation distances and altitude conflicts between aircraft pairs.
- Report detected dangers to the display and controller console.

Collaborators (if any):

- AirTraffic
- DisplayRenderer
- ControllerConsole

Assumptions (if any):

- Dangerous situations include insufficient horizontal or vertical separation between aircraft.
- Analysis runs each time the aircraft database is updated.

---

## **CRC Card**

Class: ControllerConsole

Responsibilities:

- Accept queries from the air traffic controller about specific aircraft.
- Retrieve detailed flight information for a selected aircraft.
- Present queried aircraft details to the controller.
- Display danger alerts issued by the proximity analyzer.

Collaborators (if any):

- AirTraffic
- ProximityAnalyzer

Assumptions (if any):

- The controller selects aircraft from the graphical display to query for details.
