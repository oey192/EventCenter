EventCenter
===

##Commands
(subject to change)

/event create \<name>  
Creates a new event with a name of \<name>, sets the number of teams for the event and adds whoever issued the command as the event maker  
Automatically runs /event select \<name> for the event that was just created

/event remove \<name>  
Deletes an event. /event apply must be used to confirm

/event remove regions \<name>  
Deletes regions associated with an event. This should be run before an event is deleted as it will not function afterwards

/event cancel  
Cancels a pending command that requires confirmation

/event apply  
Approves a pending command.

/event list  
List all events

/event info \<name>  
Show information about an event including the creator, 

/event tp \<name> [spawnpoint]  
Teleports the player to the default spawn point for an event. If a spawnpoint is specified, the player is teleported to that spawn point for the event instead. The player must specify the name of a team for that event or "spec" to teleport to. Anything else will result in the command failing

/event select \<name>  
Selects an event to set various properties  
"none" can be used to deselect the current event. Note that this means no event can be called "none"

/event confirm \<name>  
Confirms that the player who enters the command will be running the event at its designated start time. This command can be used up to an hour in advance of the scheduled start time of an event. If it is used outside this time frame, it will return an error. It will likewise return an error if the event does not have a scheduled reoccurring time

/event start [name]  
Starts the event \<name>. This also selects this event for any commands below that require an event to be selected (e.g. list players or teams)
If there are teams, this divides the players up as evenly as possible onto appropriate teams  
If an event is already running and no rounds are in progress, the name field doesn't need to be used. It will then start the next round

/event endround  
Ends the current round of whatever event is running
If no event is running, this returns an error

/event stop  
Stops the current event.
/event apply must be used to confirm

/event queue add \<name>  
Adds the event \<name> to the event queue. This can be done whether an event is currently being run or not

/event queue remove \<name>  
Removes \<name> from the event queue

/event queue list  
Lists the events in the queue (in the order they will run in)

/event queue next  
Ends the current event (whether it was in the queue or not) and goes to the next event in the queue. /event apply must be used to confirm

/event queue bcast [time]  
Alerts the server that the next queued event will begin in 5 minutes, or, if [time] is specified, in [time] minutes. This will not actually cause that event to start at that time  
(aliases: /event queue announce [time], /event queue broadcast [time])

/event addop \<event> \<player>  
Adds \<player> to a list of people allowed to run the event

/event remop \<event> \<player>  
Removes \<player> from the list of people allowed to run the event





######Commands that require an event to be selected or return an error

/event region set \<worldGuardRegionName> [world]  
Sets the main encapsulating region for the event  
This will fail if the event has any sub regions  
world should only need to be specified if more than one region with the same name exists across multiple worlds

/event region add \<worldGuardRegionName> [world]  
Adds a sub-region to the event. Returns an error if the region is not entirely contained in the encapsulating region for the event  
This will fail if the event has no encapsulating region

/event region remove \<worldGuardRegionName> [world]  
Removes a sub-region from the event

/event info  
See /event info \<name>

/event recurs (yes|no)  
Sets whether the event recurs or is a one-time event for testing  
Yes is default if neither yes nor no is supplied.  
e.g. "/event recurs" is the same as "/event recurs yes"

/event date add \<dayOfWeek> \<time>  
e.g. /event date add thursday 8:00PM  
Times are assumed to be in EST and will be converted to server time (CST) by the plugin

/event date remove \<dayOfWeek> \<time>  
Removes the occurrence or returns an error if it doesn't exist

/event date clear  
Removes all occurence dates from the event

/event players list  
List the current players in the event. If the event is not running, this will of course return no results

/team add \<name>  
Adds a team to the event

/team remove \<name>  
Removes the designated team from the event

/team list  
Lists the teams for the event. If the event is in progress, it includes the number of players on each team

/team scramble  
Mixes up who is on what team. This will return an error if there are 0 or 1 teams

/team sethat \<blockID|blockName>  
Sets the blockid for the helmet for the team

/team setcolor \<color>  
Sets the chat color for the team

/team \<teamName|spec> setspawn  
Sets the spawn point for \<teamName> to the your current location (including the direction you're facing)

/team \<name> maxplayers \<#|reset>  
Sets the maximum size for the team \<name>. If "reset" is specified as the number, it resets it so the team will be the same size as all other teams that do not have a set size

/team \<team> ignorescramble (on|off)  
If autoscramble is on for the event, this team will be excluded from any scrambling of teams  
Running /team \<team> ignorescramble will disable scrambling for the specified team. /team \<team> ignorescramble off will enable auto-scrambling for the team

/team \<team> additem \<quantity> \<itemID|itemName>  
Adds \<quantity> of an item to the default set of items a player will have on them when they spawn on this team. Any armor items specified will be auto-equipped. If more than one item is specified for a given armor slot, the first one encountered by the plugin will be assigned

/team \<name> removeitem \<itemID|itemName>  
Remo`ves all of \<itemID> from the list of items to give to a player upon spawning

/team \<name> listitems  
Shows a list of which items and how many of each are given to a player on that team when they spawn


/event defaultlength \<minutes>  
Sets the default round length for the event. This also sets the actual length, but this can be overridden (see below)  
If set to 0, the event will run until the EC tells it to stop or until other end conditions are met

/event setlength \<minutes|reset>  
Sets the actual round length for the event

/event numlives \<num>  
Set the maximum number of lives a player can have before being moved to spectate  
Setting this to 0 will cause players to have unlimited lives  
Negative numbers are not allowed

/event description \<description>  
Add a description for the event. If a description is already set, using this command again will overwrite the existing description

/event setscramble (on|off)  
Tells the event whether to autoscramble the teams during rounds or to leave them be  
If the event has no teams or has only one team, this has no effect  
On is default, but can be specified if desired. Off must be specified if scramble needs to be turned off



######All commands below throw an error if the regionName specified doesn't exist

/event \<regionName> add \<flagName> ['win'|'lose'] [points]  
Add \<flagName> to the event. If "win" is specified, the event will be considered over when this flag triggers and the player or team that triggers the flag will win the event. The same applies for "lose"  
If points are specified, that number of points will be given to the player that triggers the flag. Negative points are allowed

/event \<regionName> setwin \<flagName> [yes|no]  
Yes or no is optional. If neither is present, yes is assumed.  
Makes \<flagName> a winnning flag if "yes", makes it a normal flag if "no"  
If there is no flag \<flagName> on \<regionName>, an error will be returned

/event \<regionName> switchteam \<flagName> [yes|no]  
Sets whether the flag event causes the affected player to switch teams or not.  
This throws an error if there are more or less than 2 teams or if autoscramble is on for the event or any team  
Note: If this is set to true on any flag for an event, errors will be thrown if the number of teams is tried to be changed away from 2 or if you try to turn autoscramble on

/event \<regionName> setpoints \<flagName> \<points>  
Sets the number of points the flag should give to the trigger-er. Negative values are allowed

/event \<regionName> remove \<flagName>  
Removes \<flagName> from the event.  

/event \<regionName> message \<flagName> \<message>  
Sets the message to show to players when the flag is triggered. Every flag will has a default message, but this command can be used to create a custom one.  
$1 may be used to represent the name of the player who triggered the event.  
$2 may be used to represent the name of the player or block effected by the event  
Color codes may be used in the message (e.g. &4, &e, etc)


e.g. &9$1 has dropped $2
e.g. &e$1&r has killed &8$2


























###Event Notes

####General

Need support for tournament based play. E.g. elimination, etc
Way to remove all armour and/or items from players at the beginning of event (and if new player join partway through)
Should also have default set of items to give to all players upon event start and defualt set of items to auto-set as armour (this may make it not necessary to tie in with hat plugin as noted below)
Should be player configurable - EC sets default item set and another set of possible items and player can choose e.g. via command
Tie in with hat plugin to auto set a hat per team color to each player if there's more than one team
Is it possible to cause a lapis block to have the armour value of a diamond helmet?

if no item set is found for an event, clear all participant's inventories

Allow option to repeat appropriate actions from above upon respawn (if allowed)

Look into Vanilla teams - ?

Should be able to constantly make it day in event world during events (or perhaps always?)

Player death events of people who are in events should only be displayed to people in the event

Should prevent players of the opposing team from entering enemy spawn(s)

ECs will need to be able to specify which colors they want used for teams
But there should also be a way for the plugin to pick randomly


tie in with WE - have saved copy of world to reset to at beginning of round - useful for CTF (replaces flags) and breakaway (replace ice)

Make spawn point for ECs in case they need a control room (e.g. Deathrun)


Can we disable Friendly fire? And make this configurable per event?

Add/remove certain permissions to/from people when they are playing an event (likewise remove the permissions from them when they stop playing and/or join spec)

Can we disable the plugin that stops people from picking up other people's items in the event worlds and/or in certain event regions?

Add ability to have timers separate from general round timer


Need ability to set sub-regions that cause a player to "die" (aka respawn)
Perhaps have ability to set a respawn point per death pit?

Need to have sub-region that causes win when player enters it

EC should be able to set a maximum number of player winners when there's only one team (e.g. Deathrun, Evil Race)
There should be a no-team mode (every man for himself) - this will have no team chats or team colors, etc, and should have some way to configure max number of winners

Need the ability for events to cause actions such as switch player from team A to team B
e.g. if re-implementing prop-hunt, definitely will need for virus

EC needs command to announce stuff to server as well as people in events
Perhaps just /bcast and then event specific?

Is it possible to cause a redstone event to happen when EC types /event start ?

Perhaps add a lobby spawn, or just make the lobby area the spec area


Never allow sub-region of a sub-region
Main region can have as many sub regions as it wants, but there can be not sub-region that contains any part of another sub-region of the main region


events that will need to be listened for (flags)

Lever flicking
Player death
Player whacked
Player dropped item
distance from starting location - ?
PVP-enabled/disabled
friendly-fire on/off
various potions to apply to players
can an event cause a potion?
prevent all damage in this region (incl. fall damage???)





#####CTL



#####Evil Race


#####Breakaway


#####Prophunt
Is it possible to disable one team from causing any PVP damage at all?
Announce how many people are left upon death
e.g. when less that 5 people are still props, start listing how many people are left whenever someone dies
e.g. when there are less than X seconds left in the round, start listing how many people are left when time remaining is announced


#####Capture the Flag
Is it implausible to make subregions for green areas that would restrict bow usage to those areas?


#####Royal Rumble
Need ability to keep track of dominations





####Todo:
investigate WorldGuard  
perhaps just add my own flags instead of making own flag system







