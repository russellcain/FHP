# FHP (Fantasy Hockey Project)
Chance to play around with Scala, Slick, and Typescript while improving my annual fantasy hockey pool's experience.

Other ideas:
- spin up a local db and see how quickly it can be populated for tasks (aka, all team stats for the past 20 years).
- New Functionality:
    - generate a list of all the players a specified player id played with over their career
        - Bonus points for returning the team and years they shared together
        - This could be good for effective DB design and updating
        - Would be neat to extend it by degrees (so say "show me all players related to this player within 2 degrees")
    - Organize new, cleaner CC's and expose a simpler endpoint for everyone (ideally match this with UI)
    - Wrap up to create a nice set of Kaggle Datasets for others
    - Shake off some ML dust and use it to create models (would have enough data, for sure).
- Is there a way to try and make this generic enough for any datasource?
    - How difficult is it to extend out -- seek to minimize this.
- Use this to hammer down easily integrating Slick/H2/Flyway.
- Just make a nice wrapper for the kinda gross NHL API.
    - This would be contingent on finding solid/sensible CC's to use.