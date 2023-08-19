<!--
This file is part of JavaSMT,
an API wrapper for a collection of SMT solvers:
https://github.com/sosy-lab/java-smt

SPDX-FileCopyrightText: 2023 Dirk Beyer <https://www.sosy-lab.org>

SPDX-License-Identifier: Apache-2.0
-->

## Documentation:
### How to build the library:
1. First install dReal to get the Library:
   ```bash
   # Ubuntu 22.04
    sudo apt-get install curl
    curl -fsSL https://raw.githubusercontent.com/dreal/dreal4/master/setup/ubuntu/22.04/install.sh | sudo bash
   ```
2. Get all the dependencies for dReal. Use the install_prereq.sh script. 
3. Compile dreal_wrap.cxx with:
    ```
    c++ -fpic -c dreal_wrap.cxx -I/usr/lib/jvm/java-1.11.0-openjdk-amd64/include/ -I/usr/lib/jvm/java-1.11.0-openjdk-amd64/include/linux -I/opt/dreal/4.21.06.2/include -I/opt/libibex/2.7.4/include -I/opt/libibex/2.7.4/include/ibex -I/opt/libibex/2.7.4/include/ibex/3rd -I/usr/include/coin -L/opt/dreal/4.21.06.2/lib -L/opt/libibex/2.7.4/lib -L/usr/lib/x86_64-linux-gnu -L/usr/lib -ldreal -libex -lClpSolver -lClp -lCoinUtils -lbz2 -lz -llapack -lblas -lm -lnlopt
   ``` 
4. Create shared library with:
    ```
    c++ -shared dreal_wrap.o -L/opt/dreal/4.21.06.2/lib -L/opt/libibex/2.7.4/lib -L/usr/lib/x86_64-linux-gnu -L/usr/lib -ldreal -libex -lClpSolver -lClp -lCoinUtils -lbz2 -lz -llapack -lblas -lm -lnlopt -o libdreal4.so
   ```
   
    To use the just created library, you need to specifiy where to find the dependencies of ibex and 
    dreal. Copy the libraries to a lib folder and set the Java library path to where ibex and 
    dReal library is now located with `LD_LIBRARY_PATH=<tto_directory_with_dReal_and_ibex>`.
    This needs to be done, because the dependecies of dREal4 are not found. (Take a look with `ldd 
    libdreal4.so`).

    Another way would be to copy the ibex and dReal library to the folder  `/usr/lib/`. If then the 
    4th step is done with:
    ```
    c++ -shared dreal_wrap.o -L/usr/lib/x86_64-linux-gnu -L/usr/lib -ldreal -libex -lClpSolver -lClp -lCoinUtils -lbz2 -lz -llapack -lblas -lm -lnlopt -o libdreal4.so
    ```
    dReal and Ibex should be found by the system and the dReal4 lib found the two dependencies 
   and the java Library path does not to be set.

### SWIG:
It is also possible to generate new JNI code. For that a SWIG interface needs to be created and 
functions and headers need to be in that file, that should be translated.
#### Example for dReal:
- create SWIG interface (file with .i) and put functions and header in that file
- It is possible to overload operators in C++, therefore, it is possible to tell swig to rename the overloaded operators, so that those can be translated to java as well. For that use the ```rename``` command.
- There are a lot of overloads in dReal, especially in the symbolic.h file and the includes of the file. Those overloaded methods need to be handled with rename.
- The interface-file also needs a few includes for handling specific C++ class templates if the 
  translated code uses them:
    ```
    %include "std_string.i"
    %include "std_vector.i"
    %include "std_unordered_map.i"
    %include "std_pair.i"
    %include "std_shared_ptr.i"
    %include "std_set.i"
    %include "std_map.i"
    ```
- To use std::set and so on, you need to specify how the template is going to be named, so to wrap std::set with a certain type, it needs to be specified, for example:
    ```
    std::set<Formula> ->
    %template(<name>) std::set<Formula> 
    ```
    So SWIG knows how to name the generated class.
- In C++ templates can be used. A template is a construct that generates an ordinary type or function at compile time based on arguments the user supplies for the template parameters.
In order to use that, the templates must be wrapped with a type, to do so, in the interface file the template needs to be defined and wrapped like:
    ```
    namespace dreal {
    template <typename T>
    class OptionValue {
    public:
    enum class Type {
        DEFAULT,            ///< Default value
        FROM_FILE,          ///< Updated by a set-option/set-info in a file
        FROM_COMMAND_LINE,  ///< Updated by a command-line argument
        FROM_CODE,          ///< Explicitly updated by a code
    };

    /// Constructs an option value with @p value.
    explicit OptionValue(T value)
        : value_{std::move(value)}, type_{Type::DEFAULT} {}

    /// Default copy constructor.
    OptionValue(const OptionValue&) = default;

    /// Default move constructor.
    OptionValue(OptionValue&&) noexcept = default;

    /// Default copy assign operator.
    OptionValue& operator=(const OptionValue&) = default;

    /// Default move assign operator.
    OptionValue& operator=(OptionValue&&) noexcept = default;

    /// Default destructor.
    ~OptionValue() = default;

    /// Copy-assign operator for T.
    ///
    /// Note: It sets value with `Type::FROM_CODE` type.
    OptionValue& operator=(const T& value) {
        value_ = value;
        type_ = Type::FROM_CODE;
        return *this;
    }

    /// Move-assign operator for T.
    ///
    /// Note: It sets value with `Type::FROM_CODE` type.
    OptionValue& operator=(T&& value) {
        value_ = std::move(value);
        type_ = Type::FROM_CODE;
        return *this;
    }

    /// Returns the value.
    const T& get() const { return value_; }

    /// Sets the value to @p value which is given by a command-line argument.
    void set_from_command_line(const T& value) {
        if (type_ != Type::FROM_CODE) {
        value_ = value;
        type_ = Type::FROM_COMMAND_LINE;
        }
    }

    /// Sets the value to @p value which is provided from a file.
    ///
    /// @note This operation is ignored if the current value is set by
    /// command-line.
    void set_from_file(const T& value) {
        switch (type_) {
        case Type::DEFAULT:
        case Type::FROM_FILE:
            value_ = value;
            type_ = Type::FROM_FILE;
            return;

        case Type::FROM_COMMAND_LINE:
        case Type::FROM_CODE:
            // No operation.
            return;
        }
    }

    friend std::ostream& operator<<(std::ostream& os, Type type) {
        switch (type) {
        case OptionValue<T>::Type::DEFAULT:
            return os << "DEFAULT";
        case OptionValue<T>::Type::FROM_FILE:
            return os << "FROM_FILE";
        case OptionValue<T>::Type::FROM_COMMAND_LINE:
            return os << "FROM_COMMAND_LINE";
        case OptionValue<T>::Type::FROM_CODE:
            return os << "FROM_CODE";
        }
    }

    private:
    T value_;
    Type type_;
    };

    %template(OptionValueBool) OptionValue<bool>;
    %template(OptionValueInt) OptionValue<int>;
    %template(OptionValueDouble) OptionValue<double>;
    %template(OptionValueUnsignedInt) OptionValue<uint32_t>;
    }
    ```

- The order of the includes is important, so that a class that is used in different headers must be wraped first, so it is known to SWIG in other files
- To create a wrapper file with SWIG use the following command :
    - C++ 
        ```bash
        swig -c++ -java <filename>.i 
        ``` 
- Compile <file_name>_wrap.cxx to create the extension lib.so (unix). In order to compile the C++ wrappers, the compiler needs the jni.h and jni_md.h header files which are part of the JDK. They are usually in directories like this:
    ```bash
    /usr/java/include
    /usr/java/include/<operating_system>
    ```
    Use the following commands (be careful with dependencies):
    - C++:
        ```bash
        # General usage
        c++ -fpic -c <file_name>_wrap.cxx -I/usr/lib/jvm/java-1.11.0-openjdk-amd64/include/ -I/usr/lib/jvm/java-1.11.0-openjdk-amd64/include/linux  
        
        # This is specific for dReal, so that all the right dependencies are included
        c++ -fpic -c dreal_wrap.cxx -I/usr/lib/jvm/java-1.11.0-openjdk-amd64/include/ -I/usr/lib/jvm/java-1.11.0-openjdk-amd64/include/linux -I/opt/dreal/4.21.06.2/include -I/opt/libibex/2.7.4/include -I/opt/libibex/2.7.4/include/ibex -I/opt/libibex/2.7.4/include/ibex/3rd -I/usr/include/coin -L/opt/dreal/4.21.06.2/lib -L/opt/libibex/2.7.4/lib -L/usr/lib/x86_64-linux-gnu -L/usr/lib -ldreal -libex -lClpSolver -lClp -lCoinUtils -lbz2 -lz -llapack -lblas -lm -lnlopt
        ```

        For the libaries to work, the dependencies of ibex and dreal are needed. Therefore, the library is compiled with those two libraries, which should be both in the same directory:
        ```bash
        # General usage
        c++ -shared <file_name>_wrap.o -o <file_name_of_shared_lib>.so
      
        # This is specific for dReal, so that all the right dependencies are included
        c++ -shared dreal_wrap.o -L/opt/dreal/4.21.06.2/lib -L/opt/libibex/2.7.4/lib -L/usr/lib/x86_64-linux-gnu -L/usr/lib -ldreal -libex -lClpSolver -lClp -lCoinUtils -lbz2 -lz -llapack -lblas -lm -lnlopt -o libdreal4.so

        ```




