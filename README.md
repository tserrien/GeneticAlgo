Submission for the Genetic algorithm extracurricular exercise for Computational and Coginitive Neuroscience class of Maastricht University - Datascience and Knowledge Engineering faculty.

Code required a full revision from the year old skeleton. Misleading commenting and broken functions are being replaced.

The implementation that intended to stay close to the original structure (storing individuals in arrays, chromosomes in char[]) failed miserably. Though the methods themselves work, in at most 5 generations the simulation runs out of memory.
Plans to fix: 
- refactor Individual class
- refactor chromosome operations (again...)
- implement comparator to Individual
- refactor all arrays to Arraylists  