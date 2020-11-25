package javaRPG;

import java.util.Scanner;
public class rpgFirst {
	
	public static void main( String args[] ) {
		
		class Enemy{
			private double myMaxHealth = 100;
			private double myHealth = 100;
			private double myStrength;
			private double myDefense;
			private double myAgility;
			
			public Enemy( double strength, double defense, double agility ) {
				myStrength = strength;
				myDefense = defense;
				myAgility = agility;
			}
			
			//Calculations for attack sequence.  outputs: ( damage done, dodged?, damage received, dodged? )
			public double[] attack( double myStrength, double myDefense, double myAgility,
									double eStrength, double eDefense, double eAgility ) {
				double[] damage = new double[6];
				damage = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
				
				//Calculation of damage done and counter-attack damage... considers critical strike chance
				if( Math.random()*(100 - myAgility) < 5 ) {
					damage[0] = (Math.random()*.75*myStrength + .75*myStrength) - (.125*eDefense + (Math.random()*.125*eDefense));
					damage[2] = 1.0;
				} else {
					damage[0] = (.5*myStrength + Math.random()*.5*myStrength) - (.25*eDefense + Math.random()*.25*eDefense);
				}
				if( Math.random()*(100 - eAgility + myAgility) < 4 ) {
					damage[3] = (.35*eStrength + Math.random()*.35*eStrength) - (.15*myDefense + Math.random()*.15*myDefense);
					damage[5] = 1.0;
				} else {
					damage[3] = (.15*eStrength + Math.random()*.15*eStrength) - (.05*myDefense + Math.random()*.05*myDefense);
				}
				
				//Calculates whether the enemy or you dodges the attack or counter-attack
				if( Math.random()*(100 + myAgility - 2*eAgility) < 5 ) {
					damage[1] = 1.0;
				} else {
					damage[1] = 0.0;
				}
				if( Math.random()*(100 - 5*myAgility + eAgility) < 5 ) {
					damage[4] = 1.0;
				} else {
					damage[4] = 0.0;
				}
				
				return damage;
			}
			
			//Deals damage to enemy
			public void takeDamage( double damage ) {
				myHealth -= damage;
			}
			
			//Access enemy statistics
			public double getStrength() {
				return myStrength;
			}
			public double getDefense() {
				return myDefense;
			}
			public double getAgility() {
				return myAgility;
			}
			public double getHealth() {
				return myHealth;
			}
			public double getMaxHealth() {
				return myMaxHealth;
			}
			
			//Tests whether the enemy is dead
			public boolean deadTest() {
				if( getHealth() <= 0 ) {
					return true;
				} else {
					return false;
				}
			}
		}
		
		class Character{
			private double myMaxHealth = 100;
			private double myHealth = 100;
			private double myStrength;
			private double myDefense;
			private double myAgility;
			
			public Character( double strength, double defense, double agility ) {
				myStrength = strength;
				myDefense = defense;
				myAgility = agility;
			}
			
			public boolean fight( Enemy s ) {
				while( !deadTest() && !s.deadTest() ) {	
					double[] duel = attack( myStrength, myDefense, myAgility, s.getStrength(), s.getDefense(), s.getAgility() );
					if( duel[2] == 1.0 ) {
						if( !(duel[1] == 1.0) ) {
							if( ((int)(10*duel[0]))/10 <= 0 ) {
								System.out.println( "You landed a critical hit but didn't deal any damage!" );
							} else {
								System.out.println("You landed a critical hit and dealt " + ((int)(10*duel[0]))/10 + " damage!");
								s.takeDamage( ((int)(10*duel[0]))/10 );
							}
						} else {
							System.out.println("You missed a critical hit and didn't deal any damage.");
						}
					} else {
						if( !(duel[1] == 1.0) ) {
							if( ((int)(10*duel[0]))/10 <= 0) {
								System.out.println( "You lnaded a hit but didn't deal any damage!" );
							} else {
								System.out.println("You landed a hit and dealt " + ((int)(10*duel[0]))/10 + " damage!");
								s.takeDamage( ((int)(10*duel[0]))/10 );
							}
						} else {
							System.out.println("You missed and didn't deal any damage.");
						}
					}
					if( duel[5] == 1.0 ) {
						if( !(duel[4] == 1.0) ) {
							if( ((int)(10*duel[3]))/10 <= 0 ) {
								System.out.println( "Your enemy landed a critical counter-attack but didn't deal any damage!");
							} else {
								System.out.println("Your enemy landed a critical counter-attack and dealt " + ((int)(10*duel[3]))/10 + " damage!");
								takeDamage( ((int)(10*duel[3]))/10 );
							}
						} else {
							System.out.println("Your enemy missed their critical counter-attack and didn't deal any damage.");
						}
					} else {
						if( !(duel[4] == 1.0) ) {
							if( ((int)(10*duel[3]))/10 <= 0 ) {
								System.out.println("Your enemy landed a counter-attack but didn't deal any damage!");
							} else {
								System.out.println("Your enemy landed a counter-attack and dealt " + ((int)(10*duel[3]))/10 + " damage!");
								takeDamage( ((int)(10*duel[3]))/10 );
							}
						} else {
							System.out.println("Your enemy missed their counter-attack and didn't deal any damage.");
						}
					}
					System.out.println("");
					try {
			            Thread.sleep(3000);
			        } catch (InterruptedException e) {
			            e.printStackTrace();
			        }
					if( deadTest() ) {
						return false;
					} else if( !deadTest() && s.deadTest() ) {
						return true;
					}
					
					duel = s.attack( s.getStrength(), s.getDefense(), s.getAgility(), myStrength, myDefense, myAgility );
					if( duel[2] == 1.0 ) {
						if( !(duel[1] == 1.0) ) {
							if( (int)(10*duel[0])/10 <= 0.0 ){
								System.out.println("Your enemy landed a critical hit but didn't deal any damage!");
							} else {
								System.out.println("Your enemy landed a critical hit and dealt " + ((int)(10*duel[0]))/10 + " damage!");
								takeDamage( ((int)(10*duel[0]))/10 );
							}
						} else {
							System.out.println("Your enemy missed a critical hit and didn't deal any damage.");
						}
					} else {
						if( !(duel[1] == 1.0) ) {
							if( (int)(10*duel[0])/10 <= 0.0 ){
								System.out.println("Your enemy landed a hit but didn't deal any damage!");
							} else {
								System.out.println("Your enemy landed a hit and dealt " + ((int)(10*duel[0]))/10 + " damage!");
								takeDamage( ((int)(10*duel[0]))/10 );
							}
						} else {
							System.out.println("Your enemy missed and didn't deal any damage.");
						}
					}
					if( duel[5] == 1.0 ) {
						if( !(duel[4] == 1.0) ) {
							if( (int)(10*duel[3])/10 <= 0.0 ){
								System.out.println("You landed a critical counter-attack but didn't deal any damage!");
							} else {
								System.out.println("You landed a critical counter-attack and dealt " + ((int)(10*duel[3]))/10 + " damage!");
								s.takeDamage( ((int)(10*duel[3]))/10 );
							}
						} else {
							System.out.println("You missed your critical counter-attack and didn't deal any damage.");
						}
					} else {
						if( !(duel[4] == 1.0) ) {
							if( (int)(10*duel[3])/10 <= 0.0 ){
								System.out.println("You landed a counter-attack but didn't deal any damage!");
							} else {
								System.out.println("You landed a counter-attack and dealt " + ((int)(10*duel[3]))/10 + " damage!");
								s.takeDamage( ((int)(10*duel[3]))/10 );
							}
						} else {
							System.out.println("You missed your counter-attack and didn't deal any damage.");
						}
					}
					System.out.println("");
					try {
			            Thread.sleep(3000);
			        } catch (InterruptedException e) {
			            e.printStackTrace();
			        }
					
				}
				
				if( deadTest() && !s.deadTest() ) {
					return false;
				} else if( !deadTest() && s.deadTest() ) {
					return true;
				} else {
					return false;
				}
			}
			
			//Calculations for attack sequence.  outputs: ( damage done, dodged?, damage received, dodged? )
			public double[] attack( double myStrength, double myDefense, double myAgility,  double eStrength, double eDefense, double eAgility ) {
				double[] damage = new double[6];
				damage = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
				
				//Calculation of damage done and counter-attack damage... considers critical strike chance
				if( Math.random()*(100 - myAgility) < 5 ) {
					damage[0] = (Math.random()*.75*myStrength + .75*myStrength) - (.125*eDefense + (Math.random()*.125*eDefense));
					damage[2] = 1.0;
				} else {
					damage[0] = (.5*myStrength + Math.random()*.5*myStrength) - (.25*eDefense + Math.random()*.25*eDefense);
				}
				if( Math.random()*(100 - eAgility + myAgility) < 4 ) {
					damage[3] = (.35*eStrength + Math.random()*.35*eStrength) - (.15*myDefense + Math.random()*.15*myDefense);
					damage[5] = 1.0;
				} else {
					damage[3] = (.15*eStrength + Math.random()*.15*eStrength) - (.05*myDefense + Math.random()*.05*myDefense);
				}
				
				//Calculates whether the enemy or you dodges the attack or counter-attack
				if( Math.random()*(100 + myAgility - 2*eAgility) < 5 ) {
					damage[1] = 1.0;
				} else {
					damage[1] = 0.0;
				}
				if( Math.random()*(100 - 5*myAgility + eAgility) < 5 ) {
					damage[4] = 1.0;
				} else {
					damage[4] = 0.0;
				}
				
				return damage;
			}
			
			//Deals damage to character
			public void takeDamage( double damage ) {
				myHealth += -damage;
			}
			
			//Access character statistics
			public double getStrength() {
				return myStrength;
			}
			public double getDefense() {
				return myDefense;
			}
			public double getAgility() {
				return myAgility;
			}
			public double getHealth() {
				return myHealth;
			}
			public double getMaxHealth() {
				return myMaxHealth;
			}
			
			//Level up text prompts
			public void levelUpInitial() {
				System.out.println("\nYour hero has leveled up and earned two skill points!  Please select the first characteristic to level up in!");
				System.out.println("Health(H): " + getMaxHealth() + " --> " + (getMaxHealth() + 20) );
				System.out.println("Strength(S): " + getStrength() + " --> " + (getStrength()*1.2) );
				System.out.println("Defense(D): " + getDefense() + " --> " + (getDefense()*1.2) );
				System.out.println("Agility(A): " + getAgility() + " --> " + (getAgility()*1.2) );
			}
			//Second level up, to be sent after first has been selected
			public void levelUpAfterStrengthSelected() {
				System.out.println("\nPlease select the second characteristic to level up in!");
				System.out.println("Health(H): " + getMaxHealth() + " --> " + (getMaxHealth() + 20) );
				System.out.println("Defense(D): " + getDefense() + " --> " + (getDefense()*1.2) );
				System.out.println("Agility(A): " + getAgility() + " --> " + (getAgility()*1.2) );
			}
			public void levelUpAfterDefenseSelected() {
				System.out.println("\nPlease select the second characteristic to level up in!");
				System.out.println("Health(H): " + getMaxHealth() + " --> " + (getMaxHealth() + 20) );
				System.out.println("Strength(S): " + getStrength() + " --> " + (getStrength()*1.2) );
				System.out.println("Agility(A): " + getAgility() + " --> " + (getAgility()*1.2) );
			}
			public void levelUpAfterAgilitySelected() {
				System.out.println("\nPlease select the second characteristic to level up in!");
				System.out.println("Health(H): " + getMaxHealth() + " --> " + (getMaxHealth() + 20) );
				System.out.println("Strength(S): " + getStrength() + " --> " + (getStrength()*1.2) );
				System.out.println("Defense(D): " + getDefense() + " --> " + (getDefense()*1.2) );
			}
			public void levelUpAfterMaxHealthSelected() {
				System.out.println("\nPlease select the second characteristic to level up in!");
				System.out.println("Strength(S): " + getStrength() + " --> " + (getStrength()*1.2) );
				System.out.println("Defense(D): " + getDefense() + " --> " + (getDefense()*1.2) );
				System.out.println("Agility(A): " + getAgility() + " --> " + (getAgility()*1.2) );
			}
			
			//Level up stat increases
			public void levelMaxHealth() {
				myMaxHealth += 20;
				myHealth = getMaxHealth();
				System.out.println("You're hero's health has been increased to " + getMaxHealth() + " hit points!");
			}
			public void levelStrength() {
				myStrength += (int)(.2*getStrength());
				System.out.println("You're hero's strength has been increased to " + getStrength() + "!");
			}
			public void levelDefense() {
				myDefense += (int)(.2*getDefense());
				System.out.println("You're hero's defense has been increased to " + getDefense() + "!");
			}
			public void levelAgility() {
				myAgility += (int)(.2*getAgility());
				System.out.println("You're hero's agility has been increased to " + getAgility() + "!");
			}
			
			public void setHealth( double n ) {
				myHealth = n;
			}
			public void setStrength( double n ) {
				myStrength = n;
			}
			public void setDefense( double n ) {
				myDefense = n;
			}
			public void setAgility( double n ) {
				myAgility = n;
			}
			public void setMaxHealth( double n ) {
				myMaxHealth = n;
			}
			
			//Tests whether the character is dead
			public boolean deadTest() {
				if( myHealth <= 0 ) {
					return true;
				} else {
					return false;
				}
			}
		}
		
		Character hero1;
		
		//Start screen
		System.out.println( "Welcome to your quest!  Please select a character to quest with! (Respond with 'W', 'P', or 'A')" );
		System.out.println();
		System.out.println( "Warrior:                   Paladin:                   Assassin:" );
		System.out.println( "--------------------       --------------------       --------------------" );
		System.out.println( "Strength: 55.0             Strength: 45.0             Strength: 50.0" );
		System.out.println( "Defense: 45.0              Defense: 60.0              Defense: 30.0" );
		System.out.println( "Agility: 30.0              Agility: 25.0              Agility: 50.0" );
		
		Scanner in = new Scanner( System.in );
        String command = in.nextLine();
        
        //Character creation
        while(!((command.toLowerCase()).equals("w") || (command.toLowerCase()).equals("p") || (command.toLowerCase()).equals("a") ||
        	(command.toLowerCase()).equals("warrior") || (command.toLowerCase()).equals("paladin") || (command.toLowerCase()).equals("assassin"))) {
        	System.out.println("Please respond with 'W', 'P', or 'A'");
        	command = in.nextLine();
        }
        if( (command.toLowerCase()).equals("w") || (command.toLowerCase()).equals("warrior") ) {
        	System.out.println( "\nYou have selected warrior!  Please name you character!" );
        	hero1 = new Character( 55, 45, 30 );
        	command = in.nextLine();
        } else if( (command.toLowerCase()).equals("p") || (command.toLowerCase()).equals("paladin") ) {
        	System.out.println( "\nYou have selected paladin!  Please name you character!" );
        	hero1 = new Character( 45, 60, 25 );
        	command = in.nextLine();
        } else {
        	System.out.println( "\nYou have selected assassin!  Please name you character!" );
        	hero1 = new Character( 50, 30, 50 );
        	command = in.nextLine();
        }
        
        while( command.length() <= 1 || command.length() > 36 ) {
        	System.out.println( "Please select a character name with a valid number of characters (2-36)");
        	command = in.nextLine();
        }
        
        System.out.println( "\n" + command + " has been created!" );
		System.out.println( "--------------------" );
		System.out.println( "Strength: " + hero1.getStrength() );
		System.out.println( "Defense: " + hero1.getDefense() );
		System.out.println( "Agility: " +hero1.getAgility() );

		command = in.nextLine();
		
        System.out.println("\nNow, please select which quest you would like to attend to! (Respond with '1', '2', or '3')");
        System.out.println("1) The Quest for the Dragon's Claw");
        System.out.println("2) The Quest for Gildrethe's Crown");
        System.out.println("3) The Quest for the Silver Sgian-dubh");
        command = in.nextLine();
        
        while( !(command.equals("1") || command.equals("2") || command.equals("3")) ) {
        	System.out.println("Please respond with a valid number! ('1', '2', or '3')");
        	command = in.nextLine();
        }
        
        if( command.equals("1") ) {
        	System.out.println("\nYou have chosen to embark on The Quest for the Dragon's Claw!");
        	
        	command = in.nextLine();
        	
        	System.out.println("\nThere has been a rumor circulating around town that there is a formidable evil to the north." +
        						"\nFarm animals, pets, and even small children have gone missing over the past 3 weeks when" +
        						"\nwandering around to the north of the town.  Whatever it is that lurks there, the king has" + 
        						"\ntasked you to take it out, and if you return with evidence of its defeat, you will surely be" +
        						"\nrewarded handsomely.");
        	
        	command = in.nextLine();
        	
        	System.out.println("\nOne of the king's guards leads you to the northern trail.  From there you must walk alone." +
        					   "\nThe guard wishes you luck, and you head on your way up the trail.");
        	
        	try {
                Thread.sleep(7500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        	
        	
        	System.out.println("\nNot before long, you encounter what appears to be one of the ragged goblins that inhabit the" +
        					   "\nnearby cliffside.  Not a particularly difficult combatant, but it is your first true test.");
        	
        	try {
                Thread.sleep(7500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        	
        	Enemy goblin = new Enemy( 16.0, 8.0, 2.0 );
        	
        	System.out.println("\nYou have now engaged in combat with a goblin! (100 hit points)\n");
            
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            if( hero1.fight( goblin )) {
            	System.out.println("You defeated the goblin!");
            } else {
            	System.out.println("You died! Game over.");
            	in.close();
            }
            
            System.out.println( "You are now at " + hero1.getHealth() + " hit points!" );
        	
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            hero1.levelUpInitial();
            command = in.nextLine();
            int i = 0;
	        while( i == 0 ) {
            	if( (command.toLowerCase()).equals("h") || (command.toLowerCase()).equals("health") ) {
	            	hero1.levelMaxHealth();
	            	hero1.levelUpAfterMaxHealthSelected();
	            	i = 1;
	            	command = in.nextLine();
	            	while( i == 1 ) {
	            		if( (command.toLowerCase()).equals("s") || (command.toLowerCase()).equals("strength") ) {
	            			hero1.levelStrength();
	            			i = 2;
	            		} else if( (command.toLowerCase()).equals("d") || (command.toLowerCase()).equals("defense") ) {
	            			hero1.levelDefense();
	            			i = 2;
	            		} else if( (command.toLowerCase()).equals("a") || (command.toLowerCase()).equals("agility") ) {
	            			hero1.levelAgility();
	            			i = 2;
	            		} else {
	            			System.out.println("Please respond with a valid input! ('S', 'D', or 'A')");
	    	            	command = in.nextLine();
	            		}
	            	}
	            } else if( (command.toLowerCase()).equals("s") || (command.toLowerCase()).equals("strength") ) {
	            	hero1.levelStrength();
	            	hero1.levelUpAfterStrengthSelected();
	            	i = 1;
	            	command = in.nextLine();
	            	while( i == 1 ) {
	            		if( (command.toLowerCase()).equals("h") || (command.toLowerCase()).equals("health") ) {
	            			hero1.levelMaxHealth();
	            			i = 2;
	            		} else if( (command.toLowerCase()).equals("d") || (command.toLowerCase()).equals("defense") ) {
	            			hero1.levelDefense();
	            			i = 2;
	            		} else if( (command.toLowerCase()).equals("a") || (command.toLowerCase()).equals("agility") ) {
	            			hero1.levelAgility();
	            			i = 2;
	            		} else {
	            			System.out.println("Please respond with a valid input! ('H', 'D', or 'A')");
	    	            	command = in.nextLine();
	            		}
	            	}
	            } else if( (command.toLowerCase()).equals("d") || (command.toLowerCase()).equals("defense") ) {
	            	hero1.levelDefense();
	            	hero1.levelUpAfterDefenseSelected();
	            	i = 1;
	            	command = in.nextLine();
	            	while( i == 1 ) {
	            		if( (command.toLowerCase()).equals("s") || (command.toLowerCase()).equals("strength") ) {
	            			hero1.levelStrength();
	            			i = 2;
	            		} else if( (command.toLowerCase()).equals("h") || (command.toLowerCase()).equals("health") ) {
	            			hero1.levelMaxHealth();
	            			i = 2;
	            		} else if( (command.toLowerCase()).equals("a") || (command.toLowerCase()).equals("agility") ) {
	            			hero1.levelAgility();
	            			i = 2;
	            		} else {
	            			System.out.println("Please respond with a valid input! ('H', 'S', or 'A')");
	    	            	command = in.nextLine();
	            		}
	            	}
	            } else if( (command.toLowerCase()).equals("a") || (command.toLowerCase()).equals("agility") ) {
	            	hero1.levelAgility();
	            	hero1.levelUpAfterAgilitySelected();
	            	i = 1;
	            	command = in.nextLine();
	            	while( i == 1 ) {
	            		if( (command.toLowerCase()).equals("s") || (command.toLowerCase()).equals("strength") ) {
	            			hero1.levelStrength();
	            			i = 2;
	            		} else if( (command.toLowerCase()).equals("d") || (command.toLowerCase()).equals("defense") ) {
	            			hero1.levelDefense();
	            			i = 2;
	            		} else if( (command.toLowerCase()).equals("h") || (command.toLowerCase()).equals("health") ) {
	            			hero1.levelMaxHealth();
	            			i = 2;
	            		} else {
	            			System.out.println("Please respond with a valid input! ('H', 'S', or 'D')");
	    	            	command = in.nextLine();
	            		}
	            	}
	            } else {
	            	System.out.println("Please respond with a valid input! ('H', 'S', 'D', or 'A')");
	            	command = in.nextLine();
	            }
	        }
	        try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("\nYou continue along the path until you see a startled villager running towards you.  He");
            System.out.println("begins yelling and mumbling 'Help! Help!'  He begins to describe what he had just heard.");
            System.out.println("He explains the deep bellow and growl he heard issuing from the cave across the river.");
            
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            System.out.println("\nThat must be where this beast resides!  You continue on your way, towards the river");
            System.out.println("in search of the cave.");
            
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        	
            System.out.println("\nJust as the river comes into view a bandit jumps from the nearby brush!  'Gi'mee all");
            System.out.println("yu'res there belongins!' he shouts with a crossbow pointed at you.  You raise your weapon,");
            System.out.println("but certainly not to surrender it.");
            
            try {
                Thread.sleep(9000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            Enemy bandit = new Enemy( 30.0, 20.0, 20.0 );
        	
        	System.out.println("\nYou have now engaged in combat with the bandit! (100 hit points)\n");
            
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            if( hero1.fight( bandit )) {
            	System.out.println("You defeated the bandit!");
            } else {
            	System.out.println("You died!  Game over.");
            	in.close();
            }
            
            System.out.println( "You are now at " + hero1.getHealth() + " hit points!" );
            
        } else if( command.equals("2") ) {
        	System.out.println("\nYou have chosen to embark on The Quest for Gildrethe's Crown!");
        } else if( command.equals("3") ) {
        	System.out.println("\nYou have chosen to embark on The Quest for the Silver Sgian-dubh!");
        }
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        in.close();
	}
}
